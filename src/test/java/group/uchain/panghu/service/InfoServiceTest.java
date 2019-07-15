package group.uchain.panghu.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author panghu
 * @title: InfoServiceTest
 * @projectName panghu
 * @date 19-7-15 上午10:29
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class InfoServiceTest {

    @Autowired
    private InfoService infoService;

    @Test
    public void getAllUser() {

        System.out.println(infoService.getAllUser());

    }
}