package com.example.api.entities;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "categories")
public class Category {
    @Id
    @Column(name = "id")
    int id;
    @Column(name = "name")
    String name;
    @ManyToMany(mappedBy = "categories")
    List<Question> questions;
    @ManyToMany(mappedBy = "categories")
    List<Article> articles;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="parent", orphanRemoval = true)
    private List<Category> children =new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parent;
}
