package com.library;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class User {
    private int id;
    private String name;
    private String password;
    private List<Book> savedBooks = new ArrayList<>();
    public void addBook(Book book){
        savedBooks.add(book);
    }
}

