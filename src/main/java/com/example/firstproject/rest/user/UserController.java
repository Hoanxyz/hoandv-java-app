package com.example.firstproject.rest.user;

import com.example.firstproject.dto.security.AuthRequestDTO;
import com.example.firstproject.dto.security.JwtResponseDTO;
import com.example.firstproject.entity.user.UserEntity;
import com.example.firstproject.helper.jwt.JwtService;
import com.example.firstproject.service.user.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserServiceImpl userService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    public UserController(
            UserServiceImpl userService
    ) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserEntity user) {
        try {
            UserEntity newUser = userService.createUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @GetMapping("/get-user-by-name")
    public ResponseEntity<UserEntity> getUserByName(@RequestParam("name") String name) {
        UserEntity user = this.userService.getUserByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PostMapping("/login")
    public JwtResponseDTO AuthenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO){
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDTO.getUsername(), authRequestDTO.getPassword()));
            if(authentication.isAuthenticated()){
                return new JwtResponseDTO(jwtService.generateToken(authRequestDTO.getUsername()));
            } else {
                throw new UsernameNotFoundException("invalid user request..!!");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<UserEntity> updateUser(
            @PathVariable("id") Long id,
            @RequestBody UserEntity user,
            @RequestParam String password) {
        UserEntity currentUser = this.userService.findByUserId(id, password);
        currentUser.setEmail(user.getEmail());
        currentUser.setLastname(user.getLastname());
        currentUser.setFirstname(user.getFirstname());
        if (user.getPassword() != null) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            currentUser.setPassword(encodedPassword);
        }
        UserEntity userUpdated = this.userService.updateUser(currentUser, id);
        return ResponseEntity.status(HttpStatus.OK).body(userUpdated);
    }
}
