package com.library.book;

import com.library.user.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepo bookRepo;

    private final UserRepo userRepo;

    public Page<Book> getUsersSavedBookByLogin(String login, Pageable pageable){
        return bookRepo.findAllByUserId(userRepo.getUserIdByLogin(login), pageable);
    }

    public void deleteBookFromUserSavedBooks(String login, int bookId){
        bookRepo.saveBookToUser(userRepo.getUserIdByLogin(login), bookId);
    }

    public void saveBookToUser(String login, int bookId){
        int userId = userRepo.getUserIdByLogin(login);
        bookRepo.deleteBookFromUserSavedBooks(userId, bookId);
    }

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

    public void addAuthorsToBook(int bookId, List<Integer> ids){
        for (Integer id : ids) {
            bookRepo.addAuthorToBook(bookId, id);
        }
    }

    public void addBook(BookDTO bookDto){
        bookRepo.addBook(bookDto.getName(), bookDto.getYear());
        int bookId = bookRepo.getLastId();
        addAuthorsToBook(bookId, bookDto.getAuthorsIds());
    }

    public void update(BookDTO bookDto){
        switch (bookDto.getParam()){
            case NAME -> bookRepo.updateNameById(bookDto.getId(), bookDto.getName());
            case YEAR -> bookRepo.updateYearById(bookDto.getId(), bookDto.getYear());
            case AUTHOR -> {
                if(bookDto.isAddingAuthor()){
                    bookRepo.addAuthorToBook(bookDto.getId(), bookDto.getAuthorId());
                }else{
                    bookRepo.deleteAuthorFromBook(bookDto.getId(), bookDto.getAuthorId());
                }
            }
        }
    }
}
