package com.library.repository;

import com.library.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepo extends PagingAndSortingRepository<Book, Long> {
    List<Book> findBookByNameContaining(String name);

    List<Book> findBookByAuthorContaining(String author);

    List<Book> findByYear(Integer year);

    @Override
    Page<Book> findAll(Pageable pageable);

    @Query(value = "select books.id, books.name, books.author, books.year from books join savedbooks on savedbooks.bookId = books.id where savedbooks.userId = ?1;",
            countQuery = "SELECT count(*) FROM books join savedbooks on savedbooks.bookId = books.id where savedbooks.userId = ?1;",
            nativeQuery = true)
    Page<Book> findAllByUserId(int userId, Pageable pageable);

    @Modifying
    @Query(value = "delete from savedbooks where userId = ?1 and bookId = ?2",
            nativeQuery = true)
    void deleteBook(Integer userId, Integer bookId);

    @Modifying
    @Query(value = "insert into savedBooks(userId, bookId) values (?1, ?2)",
            nativeQuery = true)
    void saveBook(Integer userId, Integer bookId);

}
