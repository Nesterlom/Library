package com.library.controller;

import com.library.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepo userRepo;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Transactional
    @PostMapping("/createAcc/{name}/{password}")
    public void createAccount(@PathVariable String name,
                              @PathVariable String password) {
        userRepo.createAccount(name, password);
    }

    @Transactional
    @PostMapping("/changePass/{userId}/{newPassword}")
    public void changePassword(@PathVariable int userId,
                               @PathVariable String newPassword) {
        userRepo.changePassword(userId, passwordEncoder.encode(newPassword));
    }
}
