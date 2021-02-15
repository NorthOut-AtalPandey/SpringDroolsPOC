package com.northout.poc.springboot.demoapi.json;

import lombok.Data;

@Data
public class UserDTO {
    private String username;
    private String password;
}