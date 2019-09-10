package group.uchain.project.service.impl;

import group.uchain.project.DTO.ProjectInfo;
import group.uchain.project.result.Result;
import group.uchain.project.service.InfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InfoServiceImplTest {

    @Autowired
    private InfoService infoService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void getAllProjectInfo() {
        System.out.println(infoService.getAllProjectInfo());
    }


    @Test
    public void getDeadlineProjectInfo() {
        Result result = infoService.getDeadlineProjectInfo();
        System.out.println(result);
    }

    @Test
    public void getSize(){
        SetOperations<String, ProjectInfo> setOperations = redisTemplate.opsForSet();
        if (setOperations.size("deadline-set-key") == 0){
            System.out.println("空指针问题不存在");
        }
    }

}