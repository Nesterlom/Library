package com.library.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {
    private int id;
    private UserParamsToUpdate param;//param used in update request to define which data we want to update
    private String login;
    private String password;
    private String name;
    private String surname;
}
