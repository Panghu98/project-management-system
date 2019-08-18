package group.uchain.project_management_system.mapper;

import com.alibaba.fastjson.JSONArray;
import group.uchain.project_management_system.dto.User;
import group.uchain.project_management_system.util.SaltUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author project_management_system
 * @title: UserMapperTest
 * @projectName project_management_system
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

    @Test
    public void convertTest(){
        User user = new User();
        user.setPassword("127384");
        user.setUserId(111124314L);
        user.setUsername("刘忠慧");
        user.setSalt(SaltUtil.getSalt());
        List<User> list = new ArrayList<>();
        list.add(user);
        String str  = String.valueOf(list);

        JSONArray array = new JSONArray(Collections.singletonList(list));
        String demo = array.toJSONString();
        demo = demo.substring(1,demo.length()-1);
        System.out.println(demo);
        List<User> list1 = JSONArray.parseArray(demo,User.class);
        System.out.println(list1);
    }

}