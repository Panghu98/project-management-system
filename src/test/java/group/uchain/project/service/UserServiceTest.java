package group.uchain.project.service;

import group.uchain.project.form.RegisterUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;


    @Test
    public void register() {

        RegisterUser registerUser = new RegisterUser();
        registerUser.setUsername("胖虎");
        registerUser.setUserId(201731062632L);
        registerUser.setOffice("组织部");
        registerUser.setPosition("副教授");
        userService.register(registerUser);
    }


    @Test
    public void resetPass(){
        userService.resetPassword(201731062632L);
    }
}