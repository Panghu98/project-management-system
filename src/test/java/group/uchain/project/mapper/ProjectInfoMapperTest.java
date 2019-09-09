package group.uchain.project.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * @author project
 * @title: ProjectInfoMapperTest
 * @projectName project
 * @date 19-7-21 上午11:34
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectInfoMapperTest {

    @Autowired
    private ProjectInfoMapper projectInfoMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void minusRemainingTime(){
        projectInfoMapper.minusRemainingTime(null);
    }

    @Test
    public void getAllProjectInfo(){
        System.err.println(projectInfoMapper.getDeadlineProjectInfo());
        System.err.println(projectInfoMapper.getDeadlineProjectInfo().size());
    }


    @Test
    public void setDeadline(){
        System.out.println(projectInfoMapper.setDeadline("V25",new Date(System.currentTimeMillis())));
    }

    @Test
    public void getTime(){
        System.out.println(new Date(0));
    }

    @Test
    public void getAllOverdueProjectId(){
        System.err.println(projectInfoMapper.getAllOverdueProjectId(new Date()));
    }


    @Test
    public void getAllProjectById(){
        System.err.println(projectInfoMapper.getAllProjectInfoByUserId("123456789102"));
    }

}
