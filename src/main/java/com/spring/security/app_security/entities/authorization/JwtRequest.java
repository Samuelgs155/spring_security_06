package com.spring.security.app_security.entities.authorization;


import lombok.Data;

@Data
public class JwtRequest {
    private String username;
    private String password;

}
