package group.uchain.project_management_system.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author project_management_system
 * @title: ProjectInfoMapperTest
 * @projectName project_management_system
 * @date 19-7-21 上午11:34
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectInfoMapperTest {

    @Autowired
    private ProjectInfoMapper projectInfoMapper;

    @Test
    public void isProjectExist() {
        System.err.println(projectInfoMapper.isProjectExist("27"));
    }
}