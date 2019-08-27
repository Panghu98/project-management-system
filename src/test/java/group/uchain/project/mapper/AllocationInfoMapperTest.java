package group.uchain.project.mapper;

import group.uchain.project.vo.AllocationInfo2;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author project
 * @title: AllocationInfoMapperTest
 * @projectName project
 * @date 19-7-21 上午11:07
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AllocationInfoMapperTest {

    @Autowired
    private AllocationInfoMapper allocationInfoMapper;

    @Test
    public void uploadAllocationInfo() {

        HashMap<Long, BigDecimal> map = new HashMap<>();
        map.put(201731062632L, BigDecimal.valueOf(12));
        map.put(201731062631L, BigDecimal.valueOf(12));
        map.put(201731062630L, BigDecimal.valueOf(12));
        map.put(201731062629L, BigDecimal.valueOf(12));
        String projectId = "H27";

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
        Date start = new Date(System.currentTimeMillis()-18*60*60*24*1000);
        Date end = new Date(System.currentTimeMillis());
        System.err.println(start);
        System.err.println(end);
        List<AllocationInfo2> list = allocationInfoMapper.getAllAllocationInfo(start,end);
        System.out.println(list);
    }
}