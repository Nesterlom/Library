package com.library.service;

import com.library.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/users")
public class UserController {

    UserRepository userRepository = UserRepository.getInstance();

    @GetMapping("/createAcc/{name}/{password}")
    public void createAccount(@PathVariable String name,
                              @PathVariable String password){
        try {
            userRepository.createNewAccount(name, password);
        } catch (SQLException e) {}
    }

    @GetMapping("/changePass/{userId}/{newPassword}")
    public void changePassword(@PathVariable int userId,
                               @PathVariable String newPassword){

        try {
            userRepository.setNewPassword(userId, newPassword);
        } catch (SQLException e) {}
    }
}
