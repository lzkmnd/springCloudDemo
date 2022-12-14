package com.example.users.service;

import com.example.users.dto.UserDto;
import com.example.users.model.TbUser;

import java.util.List;

public interface UserLoginService {
    public String login(String username, String password);

    public List listUser();

    String insertOrUpdateuserDto(UserDto userDto);
}
