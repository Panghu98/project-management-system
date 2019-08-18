package group.uchain.project_management_system.service.impl;



import group.uchain.project_management_system.dto.RegisterUser;
import group.uchain.project_management_system.dto.User;
import group.uchain.project_management_system.enums.CodeMsg;
import group.uchain.project_management_system.mapper.UserFormMapper;
import group.uchain.project_management_system.result.Result;
import group.uchain.project_management_system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * @author project_management_system
 * @Title: UserServiceImpl
 * @ProjectName demo
 * @date 19-1-21 下午5:00
 */
@Service
public class UserServiceImpl implements UserService {

    private UserFormMapper userMapper;

    @Autowired
    public UserServiceImpl(UserFormMapper userMapper) {
        this.userMapper = userMapper;
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
        return new Result();
    }

}
