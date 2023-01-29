package com.library.controller;

import com.library.UserRepository;
import com.library.entity.Book;
import com.library.entity.User;
import com.library.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/users")
public class UserController {

    UserRepository userRepository = UserRepository.getInstance();

    @Autowired
    UserRepo userRepo;

    @GetMapping("/show")
    public Iterable<User> show(){
        return userRepo.findAll();
    }

    @GetMapping("/createAcc/{name}/{password}")
    public void createAccount(@PathVariable String name,
                              @PathVariable String password){
        userRepo.createAccount(name, password);
    }

    @GetMapping("/changePass/{userId}/{newPassword}")
    public void changePassword(@PathVariable int userId,
                               @PathVariable String newPassword){

        userRepo.changePassword(userId, newPassword);
    }
}
