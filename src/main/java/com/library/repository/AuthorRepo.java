package com.library.repository;

import com.library.entity.Author;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepo extends CrudRepository<Author, Integer> {
    List<Author> findAll();

    void deleteAuthorByName(String name);

    @Modifying
    @Query(value = "insert into authors(name, year) values (?1, ?2)", nativeQuery = true)
    void save(String name, int year);

    @Modifying
    @Query(value = "update authors set name = ?2 where name = ?1;", nativeQuery = true)
    void updateName(String name, String newName);

    //@Modifying
    @Query(value = "update authors set year = ?2 where name = ?1;", nativeQuery = true)
    int updateYear(String name, Integer newYear);
}
