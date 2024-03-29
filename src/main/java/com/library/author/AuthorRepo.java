package com.library.author;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepo extends JpaRepository<Author, Integer> {

    List<Author> findAll();

    void deleteAuthorById(int id);

    @Modifying
    @Query(value = "update authors set name = ?2 where id = ?1;", nativeQuery = true)
    void updateName(int id, String newName);

    @Modifying
    @Query(value = "update authors set year = ?2 where id = ?1;", nativeQuery = true)
    int updateYear(int id, Integer newYear);
}
