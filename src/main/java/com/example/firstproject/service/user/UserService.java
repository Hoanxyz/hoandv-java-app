package com.example.firstproject.service.user;

import com.example.firstproject.entity.user.UserEntity;

public interface UserService {
    UserEntity createUser(UserEntity user);

    UserEntity loginUser(UserEntity user);

    UserEntity getUserByName(String username);

    UserEntity updateUser(UserEntity user, long id);
}
