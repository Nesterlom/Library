package com.library.service;

import com.library.entity.Author;
import com.library.repository.AuthorRepo;
import com.library.repository.BookRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepo repo;

    public void save(AuthorDTO params){
        Author author = new Author();
        author.setName(params.getName());
        author.setYear(params.getYear());
        repo.save(author);
    }
}
