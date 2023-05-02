package com.library.service;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorParams {
    private int id;
    private AuthorFields field;
    private String param;
}
