package group.uchain.project_management_system.mapper;

import group.uchain.project_management_system.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author project_management_system
 * @title: UserMapper
 * @projectName project_management_system
 * @date 19-7-10 下午8:01
 */
public interface UserFormMapper {

    void register(User user);

    /**
     * 多用户注册 用户导入Excel进行注册
     * @param list  用户List
     */
    void registerMultiUser(List<User> list);

    User selectUserByUserId(long id);

    List<User> selectUserByHalfUserId(long id);
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
    List<group.uchain.project_management_system.vo.User> getAllUser();

}
