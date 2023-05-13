package com.library.Book;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookParams {
    private String name;
    private int year;
    private List<Integer> authorIds;
}
