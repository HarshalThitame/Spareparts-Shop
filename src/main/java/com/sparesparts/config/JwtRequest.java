package com.sparesparts.config;

import lombok.Data;

@Data
public class JwtRequest {
    private String username;
    private String password;
}
