package com.library.repository;


import com.library.entity.Book;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepo extends CrudRepository<Book, Integer> {

    //Book findByName(String name);

    List<Book> findByName(String name);

    List<Book> findByYear(Integer year);
}
