package com.library.service;

import com.library.entity.Book;
import lombok.Data;

import java.util.List;

@Data
public class BooksContainer {
    private List<Book> books;
    private int pageNumber;
    private int amountOfPages;
}
