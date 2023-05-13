package com.library.Book;

import com.library.Author.Author;
import com.library.Book.Book;
import lombok.Data;

import java.util.List;

@Data
public class BooksContainer {
    private List<Book> books;
    private Author author;
    private int pageNumber;
    private int amountOfPages;
}
