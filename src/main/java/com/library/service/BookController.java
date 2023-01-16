package com.library.service;

import com.library.Book;
import com.library.BookRepository;
import com.library.BooksContainer;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    BookRepository bookRepository = BookRepository.getInstance();

    @GetMapping("/{page}")
    public BooksContainer getBooks(@PathVariable int page) {
        return bookRepository.getBooks(page);
    }

    @GetMapping("/{id}/{page}")
    public BooksContainer getSavedBooks(@PathVariable int id, @PathVariable int page) {
        return bookRepository.getSavedBooks(page, id);
    }

    @GetMapping("/bookName/{name}")
    public List<Book> getBooksByName(@PathVariable String name) {
        return bookRepository.getBooksByName(name);
    }

    @GetMapping("/bookAuthor/{author}")
    public List<Book> getBooksByAuthor(@PathVariable String author) {
        return bookRepository.getBooksByAuthor(author);
    }

    @GetMapping("/bookYear/{year}")
    public List<Book> getBooksByYear(@PathVariable int year) {
        return bookRepository.getBooksByYear(year);
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
