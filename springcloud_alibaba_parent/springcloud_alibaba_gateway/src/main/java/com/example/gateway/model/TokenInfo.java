package com.example.gateway.model;

import lombok.Data;

@Data
public class TokenInfo {
    private Integer userId;
    private String userName;
    private String role;

}