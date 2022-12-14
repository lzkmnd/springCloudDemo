package com.example.users.model;


import lombok.Data;

@Data
public class TokenInfo {
    private Integer userId;
    private String userName;
    private String role;

}
