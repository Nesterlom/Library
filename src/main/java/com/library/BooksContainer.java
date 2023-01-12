package com.library;

import lombok.Data;

import java.util.List;

@Data
public class BooksContainer {
// this object will return Printer in methods connected with pagination(showBooks and showSavedBooks)

    private List<Book> books;
    private int pageNumber;
    private int amountOfPages;

}
