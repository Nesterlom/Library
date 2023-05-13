package com.library.User;

import io.swagger.annotations.ApiOperation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserRepo userRepo;

    private final UserService userService;

    @GetMapping()
    @ApiOperation("Return all users")
    public List<User> getUsers(){
        return userRepo.getUsers();
    }

    @GetMapping("/{userId}")
    @ApiOperation("Return one user by id")
    public User getUser(@PathVariable int userId){
        return userRepo.getUserById(userId);
    }

    @Transactional
    @PostMapping("/add")
    @ApiOperation("Create new account")
    public void addUser(@RequestBody UserDTO userDto) {
        userService.addUser(userDto);
    }

    @Transactional
    @PostMapping("/update")
    @ApiOperation("Update defined users data")
    public void update(@RequestBody UserDTO userDto, Principal principal) {
        userService.update(principal.getName(), userDto);
    }

    @Transactional
    @DeleteMapping("/delete")
    @ApiOperation("Delete current User")
    public void deleteCurrentUser(Principal principal){
        userRepo.deleteByLogin(principal.getName());
    }

//    @Transactional (add authorization)
//    @DeleteMapping("/delete/{userId}")
//    @ApiOperation("Delete User")
//    public void deleteUser(@PathVariable int userId){
//        userRepo.deleteById(userId);
//    }
}
