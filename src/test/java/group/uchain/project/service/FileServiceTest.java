package group.uchain.project.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileServiceTest {

    @Autowired
    private FileService fileService;

    @Test
    public void getAllocationExcel() {
        Date start = new Date(System.currentTimeMillis()-18*60*60*24*1000);
        Date end = new Date(System.currentTimeMillis());
        System.err.println(System.currentTimeMillis()-18*60*60*24*1000);
        System.err.println(System.currentTimeMillis());

    }
}