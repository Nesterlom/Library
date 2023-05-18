package com.library.book;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookDTO {
    private int id;
    private String name;
    private BookParamToUpdate param;
    private int year;
    private List<Integer> authorsIds;// when we create a book we can send the array of authors ids and they will be added to new book.
    private int authorId;// for deleting or adding to the list of book authors
    private boolean isAddingAuthor;// to understand that we want to delete or to add author to book;
}
