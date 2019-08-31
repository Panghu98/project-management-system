package group.uchain.project.rabbitmq;

import com.alibaba.fastjson.JSONArray;
import group.uchain.project.dto.ProjectInfo;
import group.uchain.project.mapper.ProjectInfoMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author project
 * @title: MQReceiver
 * @projectName project
 * @date 19-7-13 上午8:28
 */
@Slf4j
@Service
public class MQReceiver {


    private ProjectInfoMapper projectInfoMapper;

    private RedisTemplate<String, String> redisTemplate;

    private static final String FLAG_KEY = "project-info-flag";


    @Autowired
    public MQReceiver(ProjectInfoMapper projectInfoMapper,
                      RedisTemplate<String, String> redisTemplate) {
        this.projectInfoMapper = projectInfoMapper;
        this.redisTemplate = redisTemplate;
    }


    /**
     * 文件注册数据异步入库
     */
    @RabbitListener(queues = MQConfig.DIRECT_PROJECT )
    public void receiveProjectMessage(String message){
        List<ProjectInfo> list = JSONArray.parseArray(message,ProjectInfo.class);
        projectInfoMapper.excelToDatabase(list);
        //在上传项目之后,负责人的的role通过触发器升级为2
        //标记数据库是否更新
        redisTemplate.opsForValue().set(FLAG_KEY,"Y");
    }

}
