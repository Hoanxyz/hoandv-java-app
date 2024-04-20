package com.example.firstproject.service.user;

import com.example.firstproject.entity.user.UserEntity;

public interface UserService {
    UserEntity createUser(UserEntity user);

    UserEntity getUserByName(String username);

    UserEntity findByUserId(Long id, String password);

    UserEntity updateUser(UserEntity user, long id);
}
