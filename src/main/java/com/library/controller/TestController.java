package com.library.controller;

import com.library.entity.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping
    public Book test(){
        Book book = new Book();
        book.setId(1);
        book.setName("F");
        book.setAuthor("FF");
        book.setYear(2004);

        return book;
    }
}
