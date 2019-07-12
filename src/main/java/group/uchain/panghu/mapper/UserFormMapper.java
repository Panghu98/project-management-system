package group.uchain.panghu.mapper;

import group.uchain.panghu.entity.User;

/**
 * @author panghu
 * @title: UserMapper
 * @projectName panghu
 * @date 19-7-10 下午8:01
 */
public interface UserFormMapper {

    void register(User user);

    User selectUserByUserId(long id);

    void updatePassword(String userId,String password);

}
