package com.library.controller;

import com.library.entity.Book;
import com.library.repository.BookRepo;
import com.library.service.FindBy;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping()
    public Page<Book> getBooks(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return bookRepo.findAll(pageable);
    }

    @GetMapping("/{userId}")
    public Page<Book> getSavedBooks(@PathVariable int userId, @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return bookRepo.findAllByUserId(userId, pageable);
    }

    @GetMapping("/find")
    public List<Book> find(@RequestParam(value = "findBy") FindBy method,
                           @RequestParam(value = "param") String param) {
        switch (method) {
            case NAME -> {
                return bookRepo.findBookByNameContaining(param);
            }
            case AUTHOR -> {
                return bookRepo.findBookByAuthorContaining(param);
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
                           HttpServletResponse response) {
        bookRepo.deleteBook(userId, bookId);

        try {
            response.sendRedirect(String.format("/books/%d/1", userId));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
