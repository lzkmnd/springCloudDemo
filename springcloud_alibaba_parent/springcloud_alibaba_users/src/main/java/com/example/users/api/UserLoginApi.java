package com.example.users.api;

import com.example.users.dto.UserDto;
import com.example.users.model.ResultResponse;
import com.example.users.service.UserLoginService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api(tags = "用户模块")
public class UserLoginApi {
    @Autowired
    UserLoginService userLoginService;


    @ApiOperation(value = "登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "姓名", required = true),
            @ApiImplicitParam(name = "passsword", value = "密码", required = true)
    })
    @GetMapping("/user/login")
    public ResultResponse login(String username, String passsword) {
        return ResultResponse.SUCCESS(userLoginService.login(username, passsword));
    }

    @ApiOperation(value = "查询全部")
    @GetMapping("/user/list")
    public ResultResponse listUser() {
        return ResultResponse.SUCCESS(userLoginService.listUser());
    }

    @ApiOperation(value = "新增员工")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", value = "User数据", required = true, dataType = "UserDto")
    })
    @PostMapping("/user")
    public ResultResponse insertOrUpdateuserDto(@RequestBody UserDto userDto) {
        return ResultResponse.SUCCESS(userLoginService.insertOrUpdateuserDto(userDto));
    }
}
