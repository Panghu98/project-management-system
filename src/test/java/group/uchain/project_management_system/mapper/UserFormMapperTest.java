package group.uchain.project_management_system.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
}