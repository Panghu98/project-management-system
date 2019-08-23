package group.uchain.project.service;


import group.uchain.project.result.Result;

import javax.servlet.http.HttpServletRequest;

/**
 * @author project
 * @Title: LoginService
 * @ProjectName oil-supply-chain
 * @date 19-3-13 上午9:27
 */
public interface LoginService {
    /**
     * @description 用户登录接口
     * @param userId 用户名
     * @param password 密码
     * @param request http请求
     * @return 返回给前端的结果
     */
    Result login(long userId, String password, HttpServletRequest request);



}
