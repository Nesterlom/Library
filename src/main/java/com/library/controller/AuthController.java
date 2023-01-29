package com.library.controller;

import com.library.UserRepository;
import com.library.entity.User;
import com.library.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    UserRepository userRepository = UserRepository.getInstance();

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    UserRepo userRepo;

    @PostMapping
    public User auth(@RequestParam(value = "name") String name,
                     @RequestParam(value = "password") String password){
        User user = userRepo.auth(name);

        if(passwordEncoder.matches(password, user.getPassword())){
            return user;
        }
        return null;
    }
}
