package com.msys.esm.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "categories")
public class Category {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @NotBlank
    @Column(name = "name", unique = true)
    String name;

    @ManyToMany(mappedBy = "categories")
    Set<Question> questions;

    @ManyToMany(mappedBy = "categories")
    Set<Article> articles;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="parent", orphanRemoval = true,fetch = FetchType.EAGER)
    private Set<Category> children =new HashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    private Category parent;

}
