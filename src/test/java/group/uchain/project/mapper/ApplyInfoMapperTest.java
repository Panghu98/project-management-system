package group.uchain.project.mapper;

import group.uchain.project.entity.ApplyForm;
import group.uchain.project.enums.ApplyType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplyInfoMapperTest {

    @Autowired
    private ApplyInfoMapper applyInfoMapper;

    @Test
    public void addOne() {
        ApplyForm applyForm = new ApplyForm();
        applyForm.setProjectId("V27");
        applyForm.setApplyUser(123456789102L);
        applyForm.setApplyType(ApplyType.UPDATE_ALLOCATION_INFO.getApplyType());
        System.out.println(applyInfoMapper.addOne(applyForm));

    }

    @Test
    public void getAllApplyInfoNotApproval(){
        System.out.println(applyInfoMapper.getAllApplyInfoNotApproval());
    }
}