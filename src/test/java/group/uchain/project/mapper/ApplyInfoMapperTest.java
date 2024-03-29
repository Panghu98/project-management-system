package group.uchain.project.mapper;

import group.uchain.project.form.ApplyConfirmForm;
import group.uchain.project.form.ApplyForm;
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
        System.out.println(applyInfoMapper.getApplyDetailById(123456789102L));
        System.out.println(applyInfoMapper.getApplyDetailById(123456789102L).size());
    }

    @Test
    public void setApplyValidStatusByProjectId() {
        ApplyConfirmForm applyConfirmForm = new ApplyConfirmForm();
        applyConfirmForm.setApplyType(1);
        applyConfirmForm.setApprovalStatus(2);
        applyConfirmForm.setProjectId("P11");
        System.err.println(applyInfoMapper.updateApplyInfoStatus(applyConfirmForm));
    }
}