package group.uchain.project.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileInfoMapperTest {

    @Autowired
    private FileInfoMapper fileInfoMapper;

    @Test
    public void fileNameTest(){
        System.out.println(fileInfoMapper.getCompleteFileNameByProjectId("V7"));
    }

    @Test
    public void multiName(){
        List<String> list = new ArrayList<>();
        list.add("V27");
        list.add("V26");
        System.out.println(fileInfoMapper.getCompleteFileNameListByProjectId(list));
    }

}