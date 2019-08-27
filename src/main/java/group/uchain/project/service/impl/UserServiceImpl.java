package group.uchain.project.service.impl;


import group.uchain.project.dto.User;
import group.uchain.project.entity.RegisterUser;
import group.uchain.project.enums.CodeMsg;
import group.uchain.project.mapper.UserFormMapper;
import group.uchain.project.result.Result;
import group.uchain.project.security.JwtTokenUtil;
import group.uchain.project.service.UserService;
import group.uchain.project.util.MD5Util;
import group.uchain.project.util.SaltUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author project
 * @Title: UserServiceImpl
 * @ProjectName demo
 * @date 19-1-21 下午5:00
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private static final String USER_REDIS_PREFIX = "user-prefix";

    private RedisTemplate redisTemplate;

    private UserFormMapper userMapper;

    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    public UserServiceImpl(UserFormMapper userMapper,RedisTemplate redisTemplate,
                           JwtTokenUtil jwtTokenUtil) {
        this.userMapper = userMapper;
        this.redisTemplate = redisTemplate;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public User getCurrentUser() {
        String anonymousUser = "anonymousUser";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        if (!anonymousUser.equals(name)) {
            return userMapper.selectUserByUserId(Long.parseLong(name));
        }
        return null;
    }

    @Override
    public Result register(RegisterUser registerUser) {
        User user = userMapper.selectUserByUserId(registerUser.getUserId());
        if (user!=null){
            return Result.error(CodeMsg.USER_HAS_EXISTED);
        }
        user = new User(registerUser);
        userMapper.register(user);
        redisTemplate.delete(USER_REDIS_PREFIX);
        return new Result();
    }

    @Override
    public Result resetPassword(Long userId) {
        String salt = SaltUtil.getSalt();
        String password = MD5Util.inputPassToDBPass(UserService.DEFAULT_PASSWORD,salt);
        userMapper.resetPassword(userId,salt,password);
        return new Result();
    }

    @Override
    public Result updatePassword(String newPassword, HttpServletRequest request) {
        //从Token中获取用户信息,注意这里直接调用上面的getCurrentUser方法,否则会出现Bean依赖循环的问题
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        User user = userMapper.selectUserByUserId(Long.parseLong(name));
        if (user == null){
            return Result.error(CodeMsg.AUTHENTICATION_ERROR);
        }
        String salt = user.getSalt();
        //判断更改的密码和用户原密码是否相同
        String oldPassword = user.getPassword();

        String encryptNewPassword = MD5Util.inputPassToDBPass(newPassword,salt);
        if (oldPassword.equals(encryptNewPassword)) {
            log.info("新密码和原密码相同");
            return Result.error(CodeMsg.PASSWORD_UPDATE_ERROR);
        }
        long userId = user.getUserId();
        int result = userMapper.updatePassword(userId,encryptNewPassword);
        if (result == 1){
            System.err.println(request.getHeader("Authorization"));
            jwtTokenUtil.refreshToken(request.getHeader("Authorization"));
            return new Result();
        }else {
            return Result.error(CodeMsg.DATABASE_ERROR);
        }
    }

    @Override
    public Result deleteUser(Long userId) {
        if (userId == null){
            return Result.error(CodeMsg.USER_ID_IS_NULL);
        }
        User user = userMapper.selectUserByUserId(userId);
        if (user == null){
            return Result.error(CodeMsg.USER_NOT_EXIST);
        }
        if (user.getRole().equals("3")) {
            return Result.error(CodeMsg.NO_PERMISSION);
        }
        userMapper.deleteUser(userId);
        return new Result();
    }

}
