package com.library.controller;

import com.library.BookRepository;
import com.library.service.BooksContainer;
import com.library.repository.BookRepo;
import com.library.entity.Book;
import com.library.service.FindBy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    BookRepository bookRepository = BookRepository.getInstance();

    @Autowired
    BookRepo bookRepo;

    @GetMapping("/show")
    public Iterable<Book> showAll(){
        return bookRepo.findAll();
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

    @PostMapping("/add/{userId}/{bookId}")
    public String saveBook(@PathVariable Integer userId,
                         @PathVariable Integer bookId) {
        bookRepo.saveBook(userId, bookId);

        return null;
    }

    @PostMapping("/delete/{userId}/{bookId}")
    public void deleteBook(@PathVariable Integer userId,
                           @PathVariable Integer bookId) {
        bookRepo.deleteBook(userId, bookId);

    }
}
