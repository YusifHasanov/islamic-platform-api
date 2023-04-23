package com.msys.esm.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "authors")
public class Author  {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @NotBlank @Size(max = 35)
    @Column(name = "name", unique = true)
    String name;

    @NotBlank
    @Column(name = "image")
    String image;

    @JsonIgnore
    @OneToMany(mappedBy = "author")
    Set<Article> articles;

    @OneToMany(mappedBy = "author")
    Set<Book> books;
}
