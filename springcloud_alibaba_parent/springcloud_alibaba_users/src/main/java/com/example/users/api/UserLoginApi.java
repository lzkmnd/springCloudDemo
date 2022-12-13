package com.example.users.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserLoginApi {
    @GetMapping("/user/login")
    public String userLogin(@RequestParam String name) {
        return "ok";
    }
}
