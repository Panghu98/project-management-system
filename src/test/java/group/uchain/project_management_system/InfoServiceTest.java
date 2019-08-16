package group.uchain.project_management_system;

import group.uchain.project_management_system.service.InfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author project_management_system
 * @title: InfoServiceTest
 * @projectName project_management_system
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

    @Test
    public void test() throws IOException {
        Path path = Paths.get("/home/project_management_system/IdeaProjects/Project_Management_System/src/main/resources/evident/2019-07-20 15-35-44屏幕截图.png");
        Files.delete(path);
    }

}