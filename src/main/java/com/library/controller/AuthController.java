package com.library.controller;

import com.library.UserRepository;
import com.library.entity.User;
import com.library.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private final UserRepo userRepo;

    public AuthController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping
    public User auth(@RequestParam(value = "name") String name,
                     @RequestParam(value = "password") String password){
        return userRepo.findUserByName(name);
//        if(passwordEncoder.matches(password, user.getPassword())){
//            return user;
//        }
//        return null;
    }
}
