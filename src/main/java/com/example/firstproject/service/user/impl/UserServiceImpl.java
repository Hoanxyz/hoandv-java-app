package com.example.firstproject.service.user.impl;

import com.example.firstproject.entity.user.UserEntity;
import com.example.firstproject.repository.user.UserRepository;
import com.example.firstproject.service.user.UserService;
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
    public UserEntity getUserByName(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public UserEntity findByUserId(Long id, String password) {
        UserEntity user = this.userRepository.findById(id).orElse(null);
        if (user != null) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            boolean matches = passwordEncoder.matches(password, user.getPassword());
            if (matches) {
                return user;
            } else {
                throw new IllegalArgumentException("Sai mật khẩu roài.");
            }
        }
        return null;
    }

    @Override
    public UserEntity updateUser(UserEntity user, long id) {
        UserEntity existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            return userRepository.save(existingUser);
        }
        return null;
    }
}
