package group.uchain.project.mapper;

import group.uchain.project.dto.LoginInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author project
 * @title: LoginInfoMapperTest
 * @projectName project
 * @date 19-7-21 下午4:52
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginInfoMapperTest {

    @Autowired
    private LoginInfoMapper loginInfoMapper;

    @Test
    public void insert() {

        LoginInfo loginInfo = new LoginInfo();
        loginInfo.setDate(new Date());
        loginInfo.setIp("127.0.0.1");
        loginInfo.setUserId(123456L);
        loginInfoMapper.insert(loginInfo);

    }
}