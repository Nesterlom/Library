package com.library.controller;

import com.library.repository.UserRepo;
import com.library.service.Params;
import io.swagger.annotations.ApiOperation;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Transactional
    @PostMapping("/createAcc")
    @ApiOperation("Create new account")
    public void createAccount(@RequestBody Params params) {
        userRepo.createAccount(params.getUsername(), passwordEncoder.encode(params.getPassword()));
    }

    @Transactional
    @PostMapping("/changePass")
    @ApiOperation("Change users password")
    public void changePassword(@RequestBody Params params) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        userRepo.changePassword(auth.getName(), passwordEncoder.encode(params.getPassword()));
    }
}
