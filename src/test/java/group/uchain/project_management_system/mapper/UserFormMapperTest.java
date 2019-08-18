package group.uchain.project_management_system.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserFormMapperTest {

    @Autowired
    private UserFormMapper userFormMapper;

    @Test
    public void selectUserByHalfUserId() {
        Long id = 005L;
        System.out.println(userFormMapper.selectUserByHalfUserId(id));
    }

    @Test
    public void getRepeatUserId(){
        List<Long> list = new ArrayList<>();
        list.add(123456L);
        list.add(222222222222L);
        list.add(2137478923L);
        System.out.println(userFormMapper.getRepeatUserId(list));
    }

    @Test
    public void getAllUser(){
        System.out.println(userFormMapper.getAllUser());
    }
}