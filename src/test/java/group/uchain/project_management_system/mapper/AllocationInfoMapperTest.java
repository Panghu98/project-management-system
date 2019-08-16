package group.uchain.project_management_system.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

/**
 * @author project_management_system
 * @title: AllocationInfoMapperTest
 * @projectName project_management_system
 * @date 19-7-21 上午11:07
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AllocationInfoMapperTest {

    @Autowired
    private AllocationInfoMapper allocationInfoMapper;

    @Test
    public void uploadAllocationInfo() {

        HashMap<Long,Integer> map = new HashMap<>();
        map.put(201731062632L,12);
        map.put(201731062631L,12);
        map.put(201731062630L,12);
        map.put(201731062629L,12);
        String projectId = "V27";

        allocationInfoMapper.uploadAllocationInfo(map,projectId);
    }

    @Test
    public void getUserAllocationInfo() {
        String userId = "123456";
        System.err.println(allocationInfoMapper.getUserAllocationInfo(userId));
    }
}