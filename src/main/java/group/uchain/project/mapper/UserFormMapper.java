package group.uchain.project.mapper;

import group.uchain.project.dto.User;
import group.uchain.project.util.MD5Util;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author project
 * @title: UserMapper
 * @projectName project
 * @date 19-7-10 下午8:01
 */
public interface UserFormMapper {

    /**
     * 修改用户密码
     * @param userId  用户ID
     * @param salt 盐值
     * @param password 密码
     */
    void resetPassword(@Param("id") Long userId,@Param("salt") String salt,@Param("password") String password);

    /**
     * 用户注册
     * @param user 用户表单
     */
    void register(User user);

    /**
     * 多用户注册 用户导入Excel进行注册
     * @param list  用户List
     */
    void registerMultiUser(List<User> list);

    User selectUserByUserId(Long id);

    List<User> selectUserByHalfUserId(Long id);
    /**
     * 通过ID数组判断已经存在的用户
     * @param list 用户IDList
     * @return
     */
    List<Long> getRepeatUserId(@Param("list")List<Long> list);

    /**
     * 更新密码
     * @param userId  用户ID
     * @param password 新的密码
     */
    void updatePassword(long userId,String password);

    /**
     * 获取所有的用户
     * @return
     */
    List<group.uchain.project.vo.User> getAllUser();

    /**
     * 通过用户ID删除用户
     * @param userId
     */
    void deleteUser(@Param("id") Long userId);

    /**
     * 将普通用户升级为负责人
     * @param name 教师工号
     */
    void updateUserToLeader(String name);//TODO
}
