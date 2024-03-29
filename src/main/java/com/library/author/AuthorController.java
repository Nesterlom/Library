package com.library.author;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorRepo repo;

    private final AuthorService authorService;


    @GetMapping("")
    public Page<Author> showAuthors(@PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable){
        return repo.findAll(pageable);
    }

    @Transactional
    @PostMapping("/add")
    public void addAuthor(@RequestBody AuthorDTO authorDto){
        authorService.save(authorDto);
    }

    @Transactional
    @PostMapping("/update")
    public void updateAuthor(@RequestBody AuthorParams params){
        switch (params.getField()){
            case NAME -> repo.updateName(params.getId(), params.getParam());
            case YEAR -> repo.updateYear(params.getId(), Integer.parseInt(params.getParam()));
        }
    }

    @Transactional
    @DeleteMapping("/delete/{id}")
    public void deleteAuthor(@PathVariable int id){
        repo.deleteAuthorById(id);
    }

}
