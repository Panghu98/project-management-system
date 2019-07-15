package group.uchain.panghu.service.impl;

import group.uchain.panghu.mapper.UserFormMapper;
import group.uchain.panghu.result.Result;
import group.uchain.panghu.service.InfoService;
import group.uchain.panghu.vo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author panghu
 * @title: InfoServiceImpkl
 * @projectName panghu
 * @date 19-7-15 上午10:15
 */
@Service
public class InfoServiceImpl implements InfoService {

    private UserFormMapper userFormMapper;

    @Autowired
    public InfoServiceImpl(UserFormMapper userFormMapper) {
        this.userFormMapper = userFormMapper;
    }

    @Override
    public Result<List<User>> getAllUser() {
        return Result.successData(userFormMapper.getAllUser());
    }
}
