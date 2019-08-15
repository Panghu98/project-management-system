package group.uchain.project_management_system.mapper;

import group.uchain.project_management_system.entity.User;

import java.util.List;

/**
 * @author project_management_system
 * @title: UserMapper
 * @projectName project_management_system
 * @date 19-7-10 下午8:01
 */
public interface UserFormMapper {

    void register(User user);

    void registerMultiUser(List<User> list);

    User selectUserByUserId(long id);

    List<User> selectUserByHalfUserId(long id);

    void updatePassword(long userId,String password);

    List<group.uchain.project_management_system.vo.User> getAllUser();

}
