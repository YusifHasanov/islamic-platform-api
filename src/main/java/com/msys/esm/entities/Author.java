package com.msys.esm.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

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
    @Column(name = "name")
    String name;

    @NotBlank
    @Column(name = "image")
    String image;

    @OneToMany(mappedBy = "author")
    List<Article> articles;

    @OneToMany(mappedBy = "author")
    List<Book> books;
}
