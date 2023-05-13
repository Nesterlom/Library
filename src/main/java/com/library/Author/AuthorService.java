package com.library.Author;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepo repo;

    private final ModelMapper modelMapper;

    public void save(AuthorDTO authorDto){
        repo.save(convertToEntity(authorDto));

        //Author author = new Author();
        //repo.save(author);
    }

    private Author convertToEntity(AuthorDTO authorDto) throws ParseException {
        Author author = modelMapper.map(authorDto, Author.class);

        return author;
    }
}
