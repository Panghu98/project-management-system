package group.uchain.panghu.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author panghu
 * @title: TestController
 * @projectName panghu
 * @date 19-7-11 下午9:51
 */
@Api(tags = "测试")
@RestController
public class TestController {

    @GetMapping("/anon/test")
    @ApiOperation(value = "测试")
    public void test(){
        System.out.println("测试");
    }

}
