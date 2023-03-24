package com.example.api.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "books")
public class Book {
    @Id
    @Column(name = "id")
    int id;
    @Column(name = "title")
    String title;
    @ManyToOne
    @JoinColumn(name = "author_id")
    Author author;
}
