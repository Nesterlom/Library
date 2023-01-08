package com.library;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class User {
    private int id;
    private String name;
    private String password;
    //private List<Book> savedBooks;
}

