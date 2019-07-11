package group.uchain.panghu.mapper;

import group.uchain.panghu.entity.User;
import group.uchain.panghu.util.MD5Util;
import group.uchain.panghu.util.SaltUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author panghu
 * @title: UserMapperTest
 * @projectName panghu
 * @date 19-7-10 下午8:12
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    private UserFormMapper userMapper;

    @Test
    public void register() {

        User user = new User();
        user.setPassword("127384");
        user.setRole("1");
        user.setUserId(111124314L);
        user.setUsername("刘忠慧");
        user.setSalt(SaltUtil.getSalt());
        userMapper.register(user);

    }
}