package com.example.firstproject.service.user.impl;

import com.example.firstproject.entity.user.UserEntity;
import com.example.firstproject.repository.user.UserRepository;
import com.example.firstproject.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserEntity createUser(UserEntity user) {
        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("User đã tồn tại");
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    @Override
    public UserEntity loginUser(UserEntity user) {
        UserEntity existingUser = userRepository.findByUsername(user.getUsername());

        // Check if the user exists and the password matches
        if (existingUser == null || !existingUser.getPassword().equals(user.getPassword())) {
            throw new IllegalArgumentException("User name hoặc password sai.");
        }

        return existingUser;
    }

    @Override
    public UserEntity getUserByName(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public UserEntity updateUser(UserEntity user, long id) {
        UserEntity existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setSongIds(user.getSongIds());
            return userRepository.save(existingUser);
        }
        return null;
    }
}
