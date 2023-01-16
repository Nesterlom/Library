package com.library.service;

import com.library.User;
import com.library.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    UserRepository userRepository = UserRepository.getInstance();

    @GetMapping
    public User auth(@RequestParam(value = "name") String name,
                     @RequestParam(value = "password") String password){

        return userRepository.checkUser(name, password);
    }
}
