package com.library.service;

import com.library.entity.Author;
import com.library.repository.AuthorRepo;
import com.library.repository.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepo repo;

    public void save(Author params){
        Author author = new Author();
        author.setName(params.getName());
        author.setYear(params.getYear());
        repo.save(author);
    }
}
