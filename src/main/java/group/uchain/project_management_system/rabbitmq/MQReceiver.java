package group.uchain.project_management_system.rabbitmq;

import com.alibaba.fastjson.JSONArray;
import group.uchain.project_management_system.dto.LoginInfo;
import group.uchain.project_management_system.entity.ProjectInfo;
import group.uchain.project_management_system.mapper.LoginInfoMapper;
import group.uchain.project_management_system.mapper.ProjectInfoMapper;
import group.uchain.project_management_system.util.TypeConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListeners;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author project_management_system
 * @title: MQReceiver
 * @projectName project_management_system
 * @date 19-7-13 上午8:28
 */
@Slf4j
@Service
public class MQReceiver {

    private LoginInfoMapper loginInfoMapper;

    private ProjectInfoMapper projectInfoMapper;

    @Autowired
    public MQReceiver(LoginInfoMapper loginInfoMapper,ProjectInfoMapper projectInfoMapper) {
        this.loginInfoMapper = loginInfoMapper;
        this.projectInfoMapper = projectInfoMapper;
    }

    /**
     * 登录消息入库进行操作
     * @param message
     */
    @RabbitListener(queues = MQConfig.DIRECT_LOGIN)
    public void receiveLoginMessage(String message){
        LoginInfo info = TypeConvertUtil.stringToBean(message,LoginInfo.class);
        log.info("用户"+info.getUserId()+"在"+info.getDate()+"进行登录操作  "+"ip为"+info.getIp());
        loginInfoMapper.insert(info);
    }

    /**
     * 文件注册数据异步入库
     */
    @RabbitListener(queues = MQConfig.DIRECT_PROJECT )
    public void receiveProjectMessage(String message){
        List<ProjectInfo> list = JSONArray.parseArray(message,ProjectInfo.class);
        projectInfoMapper.readExcel(list);
    }

}
