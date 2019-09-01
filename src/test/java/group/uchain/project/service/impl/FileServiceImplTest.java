package group.uchain.project.service.impl;

import group.uchain.project.service.FileService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class FileServiceImplTest {

    @Autowired
    private FileService fileService;

    @Test
    public void getAllFilesName() {
        System.out.println(fileService.getAllFilesName());
    }
}