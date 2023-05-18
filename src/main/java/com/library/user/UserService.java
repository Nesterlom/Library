package com.library.user;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.expression.ParseException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;

    private final ModelMapper modelMapper;

    private final UserRepo userRepo;


    public void addUser(UserDTO userDto){
        User user = convertToEntity(userDto);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    public void update(String currentUserLogin, UserDTO userDto){
        switch (userDto.getParam()){
            case LOGIN -> {
                userRepo.updateLoginByLogin(currentUserLogin, userDto.getLogin());
            }
            case PASSWORD -> {
                userRepo.updatePasswordByLogin(currentUserLogin, passwordEncoder.encode(userDto.getPassword()));
            }
            case NAME -> {
                userRepo.updateNameByLogin(currentUserLogin, userDto.getName());
            }
            case SURNAME -> {
                userRepo.updateSurnameByLogin(currentUserLogin, userDto.getSurname());
            }
        }
    }

    private User convertToEntity(UserDTO userDto) throws ParseException {
        User user = modelMapper.map(userDto, User.class);
        return user;
    }
}
