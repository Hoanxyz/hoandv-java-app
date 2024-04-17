package com.example.firstproject.rest.response;

import com.example.firstproject.entity.user.UserEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
    UserEntity user;

    String token;

    public LoginResponse(UserEntity user, String token) {
        this.user = user;
        this.token = token;
    }
}
