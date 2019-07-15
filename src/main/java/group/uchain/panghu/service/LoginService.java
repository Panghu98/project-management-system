package group.uchain.panghu.service;


import group.uchain.panghu.result.Result;

/**
 * @author panghu
 * @Title: LoginService
 * @ProjectName oil-supply-chain
 * @date 19-3-13 上午9:27
 */
public interface LoginService {
    /**
     * @description 用户登录接口
     * @param userId 用户名
     * @param password 密码
     * @return 返回给前端的结果
     */
    Result login(long userId, String password);

    /**
     * 用户更改密码
     * @param newPassword
     * @return
     */
    Result updatePassword(String newPassword);


}
