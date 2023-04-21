package com.library.controller;

import com.library.entity.Book;
import com.library.repository.BookRepo;
import com.library.service.BookParams;
import com.library.service.BookService;
import com.library.service.FindBy;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@Api("Controller to work with books.")
public class BookController {
    private final BookRepo bookRepo;

    @Autowired
    private BookService bookService;

    @GetMapping()
    @ApiOperation("Get one page of books")
    public Page<Book> getBooks(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return bookRepo.findAll(pageable);
    }

    @GetMapping("/{userId}")
    @ApiOperation("Get page of users saved books")
    public Page<Book> getSavedBooks(@PathVariable int userId, @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return bookRepo.findAllByUserId(userId, pageable);
    }

    @GetMapping("/find")
    @ApiOperation("Find book by name, author or year")
    public List<Book> find(@RequestParam(value = "findBy") FindBy method,
                           @RequestParam(value = "param") String param) {
        return bookService.findBook(method, param);
    }

    @Transactional
    @PostMapping("/save/{userId}/{bookId}")
    @ApiOperation("Save book to users saved books")
    public void saveBook(@PathVariable Integer userId,
                         @PathVariable Integer bookId) {
        bookRepo.saveBook(userId, bookId);
    }

    @Transactional
    @DeleteMapping("/delete/{userId}/{bookId}")
    @ApiOperation("Delete book from users saved books")
    public void deleteSavedBook(@PathVariable Integer userId,
                           @PathVariable Integer bookId) {
        bookRepo.deleteBook(userId, bookId);
    }
    //😊
    @Transactional
    @PostMapping("/add")
    public void addBook(@RequestBody BookParams params){
        bookService.addBook(params);
    }

    @Transactional
    @DeleteMapping("/delete/{bookName}")
    public void deleteBook(@PathVariable String bookName){
        bookRepo.deleteBookByName(bookName);
    }
}
