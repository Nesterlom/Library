package com.library.book;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Api("Controller to work with books.")
public class BookController {
    private final BookRepo bookRepo;

    private final BookService bookService;

    @GetMapping("/all")
    @ApiOperation("Get one page of books")
    public Page<Book> getBooks(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return bookRepo.findAll(pageable);
    }

    @GetMapping()
    @ApiOperation("Get page of users saved books")
    public Page<Book> getSavedBooks(Principal principal, @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return bookService.getUsersSavedBookByLogin(principal.getName(), pageable);
    }

    @GetMapping("/find")
    @ApiOperation("Find book by name, author or year")
    public List<Book> find(@RequestParam(value = "findBy") FindBy method,
                           @RequestParam(value = "param") String param) {
        return bookService.findBook(method, param);
    }

    @Transactional
    @PostMapping("/save/")
    @ApiOperation("Save book to users saved books")
    public void saveBookToUser(Principal principal, @RequestBody BookDTO bookDto) {
        bookService.saveBookToUser(principal.getName(), bookDto.getId());
    }

    @Transactional
    @DeleteMapping("/delete/{bookId}")
    @ApiOperation("Delete your saved book")
    public void deleteSavedBook(Principal principal, @PathVariable Integer bookId) {
        bookService.deleteBookFromUserSavedBooks(principal.getName(), bookId);
    }

    @Transactional
    @PostMapping("/add")
    public void addBook(@RequestBody BookDTO bookDto){
        bookService.addBook(bookDto);
    }

    @Transactional// add authorization and give response only to admins
    @DeleteMapping("/delete")
    public void deleteBook(@RequestBody BookDTO bookDto){
        bookRepo.deleteBookById(bookDto.getId());
    }

    @PostMapping("/update")// add authorization and give response only to admins
    public void updateBook(@RequestBody BookDTO bookDto){
        bookService.update(bookDto);
    }
}
