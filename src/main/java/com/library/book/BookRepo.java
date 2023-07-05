package com.library.book;

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

    @Query(value = "select books.id, books.name, books.year from books, authors join books_authors ba on authors.id = ba.author_id join authors a on ba.author_id = a.id where authors.name like ?1", nativeQuery = true)
    List<Book> findBookByAuthorContaining(String name);

    List<Book> findByYear(int year);

    Page<Book> findAll(Pageable pageable);

//    @Query(value = "select books.id, books.name, books.author, books.year from books join savedbooks on savedbooks.bookId = books.id where savedbooks.userId = ?1;",
//            countQuery = "SELECT count(*) FROM books join savedbooks on savedbooks.bookId = books.id where savedbooks.userId = ?1;",
//            nativeQuery = true)
//    Page<Book> findAllByUserId(int userId, Pageable pageable);

    @Query(value = "select books.id, books.name, books.year from books join savedbooks on savedbooks.book_id = books.id where savedbooks.user_id = ?1;",
            countQuery = "SELECT count(*) FROM books join savedbooks on savedbooks.book_id = books.id where savedbooks.user_id = ?1;",
            nativeQuery = true)
    Page<Book> findAllByUserId(int userId, Pageable pageable);

    @Modifying
    @Query(value = "delete from savedbooks where user_id = ?1 and book_id = ?2",
            nativeQuery = true)
    void deleteBookFromUserSavedBooks(int userId, int bookId);

    @Modifying
    @Query(value = "insert into savedBooks(user_id, book_id) values (?1, ?2)",
            nativeQuery = true)
    void saveBookToUser(int userId, int bookId);

    @Modifying
    @Query(value = "insert into books(name, year) values (?1, ?2)", nativeQuery = true)
    void addBook(String name, int year);

    void deleteBookById(int bookId);

    @Query(value = "select * from books order by id desc limit 1;", nativeQuery = true)
    int getLastId();

    @Modifying
    @Query(value = "insert into books_authors(book_id, author_id) values (?1, ?2)",
            nativeQuery = true)
    void addAuthorToBook(int bookId, int authorId);

    @Modifying
    @Query(value = "delete from books_authors where author_id = ?2 and book_id = ?1",
            nativeQuery = true)
    void deleteAuthorFromBook(int bookId, int authorId);

    @Modifying
    @Query(value = "update books set name = ?2 where id = ?1",nativeQuery = true)
    void updateNameById(int id, String name);

    @Modifying
    @Query(value = "update books set year = ?2 where id = ?1",nativeQuery = true)
    void updateYearById(int id, int year);

    //void updateAuthorsById(int id, );
}
