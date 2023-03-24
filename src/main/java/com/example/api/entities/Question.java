package com.example.api.entities;
import jakarta.persistence.*;
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
@Table(name = "questions")
public class Question {
    @Id
    @Column(name = "id")
    int id;
    @Column(name = "question")
    String question;
    @Column(name = "answer")
    String answer;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "question_category",
            joinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "question_id", referencedColumnName = "id"))
    List<Category> categories;
}
