package com.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "authors")
public class Author {
    @Id
    @GeneratedValue
    private Integer id;
    private String name;
    private int year;

    @JsonIgnoreProperties(value = "authors")
    @ManyToMany(mappedBy = "authors", cascade =  CascadeType.REMOVE)
    private List<Book> books;
}
