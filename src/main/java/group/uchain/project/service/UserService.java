package group.uchain.project.service;


import group.uchain.project.dto.RegisterUser;
import group.uchain.project.dto.User;
import group.uchain.project.result.Result;

/**
 * @author dgh
 * @date 19-1-18 下午3:14
 */
public interface UserService {

    /**
     * @return  当前登录用户的信息
     */
    User getCurrentUser();

    /**
     * 超级管理员通过表单注册用户
     * @param user
     * @return
     */
    Result register(RegisterUser user);

}