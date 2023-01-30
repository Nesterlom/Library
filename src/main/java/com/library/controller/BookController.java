package com.library.controller;

import com.library.BookRepository;
import com.library.service.BooksContainer;
import com.library.repository.BookRepo;
import com.library.entity.Book;
import com.library.service.FindBy;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookRepo bookRepo;

    public BookController(BookRepo bookRepo) {
        this.bookRepo = bookRepo;
    }

    @GetMapping("/show")
    public List<Book> showAll(){
        return (List<Book>) bookRepo.findAll();
    }

    @GetMapping("/{page}")
    public BooksContainer getBooks(@PathVariable int page) {
        int limit = page * 10;
        int offset = limit - 10;

        BooksContainer bc = new BooksContainer();
        bc.setBooks(bookRepo.getBooks(limit, offset));
        bc.setPageNumber(page);
        bc.setAmountOfPages( (int) Math.ceil(bookRepo.getAmountOfBooks() / 10.0));
        return bc;
    }

    @GetMapping("/{userId}/{page}")
    public BooksContainer getSavedBooks(@PathVariable int page, @PathVariable int userId) {
        int limit = page * 10;
        int offset = limit - 10;

        BooksContainer bc = new BooksContainer();
        bc.setBooks(bookRepo.getSavedBooks(userId, limit, offset));
        bc.setPageNumber(page);
        bc.setAmountOfPages( (int) Math.ceil(bookRepo.getAmountOfSavedBooks(userId) / 10.0));

        return bc;
    }

    @GetMapping("/find")
    public List<Book> find(@RequestParam(value = "findBy") FindBy method,
                           @RequestParam(value = "param") String param) {
        switch (method){
            case NAME -> {
                return bookRepo.findByName(param);
            }
            case AUTHOR -> {
                return bookRepo.findByAuthor(param);
            }
            case YEAR -> {
                return bookRepo.findByYear(Integer.parseInt(param));
            }
        }
        return null;
    }

    @Transactional
    @PostMapping("/add/{userId}/{bookId}")
    public void saveBook(@PathVariable Integer userId,
                         @PathVariable Integer bookId,
                           HttpServletResponse response) {
        bookRepo.saveBook(userId, bookId);

        try {
            response.sendRedirect(String.format("/books/%d/1", userId));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    @PostMapping("/delete/{userId}/{bookId}")
    public void deleteBook(@PathVariable Integer userId,
                             @PathVariable Integer bookId,
                             HttpServletResponse response){
        bookRepo.deleteBook(userId, bookId);

        try {
            response.sendRedirect(String.format("/books/%d/1", userId));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
