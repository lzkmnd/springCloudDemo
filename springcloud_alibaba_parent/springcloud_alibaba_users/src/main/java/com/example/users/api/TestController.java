package com.example.users.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api")
@RefreshScope

public class TestController {

    @Value("${nacosConfigTest.name:false}")
    private String name;
    @Value("${nacosConfigTest.value:false}")
    private String value;

    @GetMapping("/configTest")
    public String configTest(){
        return "获取的值："+name+":"+value;
    }
}
