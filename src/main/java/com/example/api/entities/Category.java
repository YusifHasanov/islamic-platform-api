package com.example.api.entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @NotBlank
    @Column(name = "name")
    String name;
    @ManyToMany(mappedBy = "categories")
    List<Question> questions;
    @ManyToMany(mappedBy = "categories")
    List<Article> articles;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="parent", orphanRemoval = true,fetch = FetchType.EAGER)
    private List<Category> children =new ArrayList<>();
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    private Category parent;
}
