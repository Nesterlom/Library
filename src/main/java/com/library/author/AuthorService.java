package com.library.author;

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
    }

    private Author convertToEntity(AuthorDTO authorDto) throws ParseException {
        return modelMapper.map(authorDto, Author.class);
    }
}
