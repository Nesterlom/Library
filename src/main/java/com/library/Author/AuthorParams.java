package com.library.Author;

import com.library.Author.AuthorFields;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthorParams {
    private Integer id;
    private AuthorFields field;
    private String param;
}
