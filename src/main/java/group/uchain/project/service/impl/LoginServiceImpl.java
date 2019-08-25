package group.uchain.project.service.impl;


import group.uchain.project.dto.LoginInfo;
import group.uchain.project.dto.User;
import group.uchain.project.enums.CodeMsg;
import group.uchain.project.mapper.UserFormMapper;
import group.uchain.project.rabbitmq.MQSender;
import group.uchain.project.result.Result;
import group.uchain.project.security.JwtTokenUtil;
import group.uchain.project.service.LoginService;
import group.uchain.project.service.UserService;
import group.uchain.project.util.IpUtil;
import group.uchain.project.util.MD5Util;
import group.uchain.project.util.RoleConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;

/**
 * @author dgh
 * @date 19-1-19 下午12:57
 */
@Slf4j
@Service
public class LoginServiceImpl implements LoginService {

    private AuthenticationManager authenticationManager;

    private JwtTokenUtil jwtTokenUtil;

    private UserDetailsService userDetailsService;

    private UserFormMapper userFormMapper;

    private MQSender mqSender;

    @Autowired
    public LoginServiceImpl(AuthenticationManager authenticationManager, JwtTokenUtil jwtTokenUtil,
                            UserDetailsService userDetailsService, UserFormMapper userFormMapper,
                            MQSender mqSender) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
        this.userFormMapper = userFormMapper;
        this.mqSender = mqSender;
    }

    @Override
    public Result login(long userId, String password, HttpServletRequest request) {
        if (userFormMapper.selectUserByUserId(userId) == null) {
            log.info("用户不存在");
            return Result.error(CodeMsg.USER_NOT_EXIST);
        }

        //验证用户密码
        log.info("进行验证用户密码..");
        User user = userFormMapper.selectUserByUserId(userId);
        String salt = user.getSalt();
        String realPassword = MD5Util.formPassToDBPass(password,salt);
        if (!realPassword.equals(user.getPassword())){
            log.info("用户"+userId+"密码错误");
            return Result.error(CodeMsg.PASSWORD_ERROR);
        }

        Authentication token1 = new UsernamePasswordAuthenticationToken(userId, realPassword);
        Authentication authentication = authenticationManager.authenticate(token1);
        log.info("验证通过..");
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //返回的对象中是带有权限信息的
        final UserDetails userDetails = userDetailsService.loadUserByUsername(String.valueOf(userId));
        log.info("加载userDetails:" + userDetails.getUsername());

        //异步记录登录信息入库
        LoginInfo info = new LoginInfo();
        info.setDate(new Date());
        info.setUserId(userId);
        info.setIp(IpUtil.getIpAddress(request));
        mqSender.sendLoginInfo(info);

        //将UserDetails放入Token的 payload中
        final String token = jwtTokenUtil.generateToken(userDetails);
        HashMap<String, String> r = new HashMap<>(10);
        r.put("token", token);
        r.put("flag",user.getRole().toString());
        r.put("role", RoleConvertUtil.getDescription(user.getRole()));
        r.put("username",user.getUsername());
        System.out.println("-------------------------------------------------------");
        System.out.println(token);
        System.out.println("--------------------------------------------------------");

        return Result.successData(r);
    }





}
