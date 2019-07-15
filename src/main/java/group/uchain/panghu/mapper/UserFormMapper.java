package group.uchain.panghu.mapper;

import group.uchain.panghu.entity.User;

import java.util.List;

/**
 * @author panghu
 * @title: UserMapper
 * @projectName panghu
 * @date 19-7-10 下午8:01
 */
public interface UserFormMapper {

    void register(User user);

    void registerMultiUser(List<User> list);

    User selectUserByUserId(long id);

    void updatePassword(long userId,String password);

    List<group.uchain.panghu.vo.User> getAllUser();

}
