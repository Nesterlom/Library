package com.library.entity;

import com.library.entity.Book;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;
    private String password;

    @ManyToMany
    @JoinTable(name = "savedbooks", joinColumns = @JoinColumn (name = "userId"),
    inverseJoinColumns = @JoinColumn(name = "bookId"))
    private List<Book> savedBooks = new ArrayList<>();
    public void addBook(Book book){
        savedBooks.add(book);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Book> getSavedBooks() {
        return savedBooks;
    }

    public void setSavedBooks(List<Book> savedBooks) {
        this.savedBooks = savedBooks;
    }
}

