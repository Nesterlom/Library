package com.library.Book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepo bookRepo;

    public List<Book> findBook(FindBy method, String param){
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

    public void addAuthorToBook(int bookId, List<Integer> ids){
        for (Integer id : ids) {
            bookRepo.addAuthorToBook(bookId, id);
        }
    }

    public void addBook(BookParams params){
        bookRepo.addBook(params.getName(), params.getYear());
        int bookId = bookRepo.getLastId();
        addAuthorToBook(bookId, params.getAuthorIds());
    }
}
