package com.msys.esm.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.msys.esm.Core.Util.Validators.Concretes.TrimValidator;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "authors", uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
public class Author  {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @NotBlank
    @Size(max = 35)
    @Convert(converter = TrimValidator.class)
    @Column(name = "name", unique = true)
    String name;

    @NotBlank
    @Convert(converter = TrimValidator.class)
    @Column(name = "image")
    String image;

    @JsonIgnore
    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    Set<Article> articles;

    @OneToMany(mappedBy = "author", fetch = FetchType.LAZY)
    Set<Book> books;

}
