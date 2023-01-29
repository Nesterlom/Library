package com.library.repository;

import com.library.entity.Book;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepo extends CrudRepository<Book, Long> {

    @Query(value = "select * from books where name like %?1%",
            nativeQuery = true)
    List<Book> findByName(String name);

    @Query(value = "select * from books where author like %?1%",
            nativeQuery = true)
    List<Book> findByAuthor(String author);

    List<Book> findByYear(Integer year);

    @Query(value = "select count(id) from books",
            nativeQuery = true)
    int getAmountOfBooks();

    @Query(value = "select count(savedbooks.bookId) from savedbooks where userId = 10;",
            nativeQuery = true)
    int getAmountOfSavedBooks(int userId);

    @Query(value = "select * from books limit ?1 offset ?2",
            nativeQuery = true)
    List<Book> getBooks(int limit, int offset);

    @Query(value = "select id, name, author, year from books join savedBooks on savedBooks.bookId = books.id where userId = ?1 limit ?2 offset ?3",
            nativeQuery = true)
    List<Book> getSavedBooks(int userId, int limit, int offset);

    @Modifying
    @Query(value = "delete from savedbooks where userId = ?1 and bookId = ?2",
            nativeQuery = true)
    void deleteBook(Integer userId, Integer bookId);

    @Modifying
    @Query(value = "insert into savedBooks(userId, bookId) values (:userId, :bookId)",
            nativeQuery = true)
    void saveBook(@Param("userId") Integer userId, @Param("bookId") Integer bookId);
}
