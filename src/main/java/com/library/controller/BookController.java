package com.library.controller;

import com.library.BookRepository;
import com.library.BooksContainer;
import com.library.repository.BookRepo;
import com.library.entity.Book;
import com.library.service.FindBy;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    BookRepository bookRepository = BookRepository.getInstance();

    @Autowired
    BookRepo bookRepo;

    @GetMapping("/show")
    public Iterable<Book> show(){
        return bookRepo.findAll();
    }

    @GetMapping("/{page}")
    public BooksContainer getBooks(@PathVariable int page) {
        return bookRepository.getBooks(page);
    }

    @GetMapping("/{id}/{page}")
    public BooksContainer getSavedBooks(@PathVariable int userId, @PathVariable int page) {
        return bookRepository.getSavedBooks(page, userId);
    }

    @GetMapping("/find")
    public List<Book> find(@RequestParam(value = "findBy") FindBy method,
                           @RequestParam(value = "param") String param) {
        switch (method){
            case NAME -> {
                return bookRepo.findByName(param);
                //return bookRepository.getBooksByName(param);
            }
            case AUTHOR -> {
                return bookRepository.getBooksByAuthor(param);
            }
            case YEAR -> {
                return bookRepository.getBooksByYear(Integer.parseInt(param));
            }
        }
        return null;
    }

    @GetMapping("/add/{userId}/{bookId}")
    public void saveBook(@PathVariable int userId,
                         @PathVariable int bookId,
                         HttpServletResponse response) {
        bookRepository.saveBook(userId, bookId);

        try {
            response.sendRedirect("/books/10/1");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/delete/{userId}/{bookId}")
    public void deleteBook(@PathVariable int userId,
                           @PathVariable int bookId,
                           HttpServletResponse response) {
        bookRepository.deleteBook(userId, bookId);

        try {
            response.sendRedirect("/books/10/1");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
