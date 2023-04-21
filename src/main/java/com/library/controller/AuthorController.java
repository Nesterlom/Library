package com.library.controller;

import com.library.entity.Author;
import com.library.repository.AuthorRepo;
import com.library.service.AuthorFields;
import com.library.service.AuthorService;
import com.library.service.FindBy;
import com.library.service.Params;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    @Autowired
    private final AuthorRepo repo;

    @Autowired
    private AuthorService authorService;

    @GetMapping("")
    public List<Author> showAuthors(){
        return repo.findAll();
    }

    @Transactional
    @PostMapping("add")
    public void addAuthor(@RequestBody Author params){
        authorService.save(params);
    }

//    @Transactional
//    @PostMapping("/update")
//    public void updateAuthor(@RequestParam(value = "field") AuthorFields field,
//                             @RequestParam(value = "name") String name,
//                             @RequestParam(value = "param") String param){
//        switch (field){
//            case NAME -> {
//                repo.updateName(name, param);
//            }
//            case YEAR -> {
//                repo.updateYear(name, Integer.parseInt(param));
//            }
//        }
//    }

    @Transactional
    @DeleteMapping("/delete/{authorName}")
    public void deleteAuthor(@PathVariable String authorName){
        repo.deleteAuthorByName(authorName);
    }
}
