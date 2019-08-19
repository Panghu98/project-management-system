package group.uchain.project.service.impl;

import group.uchain.project.service.InfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sound.sampled.Line;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InfoServiceImplTest {

    @Autowired
    private InfoService infoService;

    @Test
    public void getAllProjectInfo() {
        System.out.println(infoService.getAllProjectInfo());
    }
}