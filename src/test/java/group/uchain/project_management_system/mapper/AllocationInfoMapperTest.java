package group.uchain.project_management_system.mapper;

import group.uchain.project_management_system.vo.AllocationInfo2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

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

    @Test
    public void  insertAllocationTime(){
        allocationInfoMapper.updateAllocationTime(new Date(),"V27");
    }

    @Test
    public void getAllAllocationInfo(){
        Date start = new Date(System.currentTimeMillis()-60*60*60*24*50);
        Date end = new Date(System.currentTimeMillis());
        System.err.println(start);
        System.err.println(end);
        List<AllocationInfo2> list = allocationInfoMapper.getAllAllocationInfo(start,end);
        System.out.println(list);
    }
}