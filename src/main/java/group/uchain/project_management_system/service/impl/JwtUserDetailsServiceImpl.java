package group.uchain.project_management_system.service.impl;

import group.uchain.project_management_system.entity.User;
import group.uchain.project_management_system.mapper.UserFormMapper;
import group.uchain.project_management_system.security.JwtUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author project_management_system
 */
@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

    private UserFormMapper userFormMapper;

    @Autowired
    public JwtUserDetailsServiceImpl(UserFormMapper userFormMapper) {
        this.userFormMapper = userFormMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        long userId = Long.parseLong(username);

        if (userFormMapper.selectUserByUserId(userId) == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            User user = userFormMapper.selectUserByUserId(userId);
            String password = user.getPassword();
            String role =  user.getRole();
            //返回一个JwtUser并将这个对象的相关信息放入Token的负载当中,调用JwtTokenUtils中的generateToken方法
            //这里其实可以不放入role,获取role的方式还是在security中的Context获取用户名.再通过用户名查询role
            return new JwtUser(username, password,role);
        }

    }
}
