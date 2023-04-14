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

    @Query(value = "select books.id, books.name, books.year from books, authors join books_authors ba on authors.id = ba.author_id join authors a on ba.author_id = a.id where authors.name like %?1%", nativeQuery = true)
    List<Book> findBookByAuthorContaining(String name);

    List<Book> findByYear(Integer year);

    Page<Book> findAll(Pageable pageable);

    @Query(value = "select books.id, books.name, books.author, books.year from books join savedbooks on savedbooks.bookId = books.id where savedbooks.userId = ?1;",
            countQuery = "SELECT count(*) FROM books join savedbooks on savedbooks.bookId = books.id where savedbooks.userId = ?1;",
            nativeQuery = true)
    Page<Book> findAllByUserId(int userId, Pageable pageable);

    @Modifying
    @Query(value = "delete from savedbooks where user_id = ?1 and book_id = ?2",
            nativeQuery = true)
    void deleteBook(Integer userId, Integer bookId);

    @Modifying
    @Query(value = "insert into savedBooks(user_id, book_id) values (?1, ?2)",
            nativeQuery = true)
    void saveBook(Integer userId, Integer bookId);

    @Modifying
    @Query(value = "insert into books(name, year) values (?1, ?2)", nativeQuery = true)
    void addBook(String name, int year);

    void deleteBookByName(String name);
}
