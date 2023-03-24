package com.example.api.entities;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.util.Date;
import java.util.List;
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "articles")
public class Article {
    @Id
    @Column(name = "id")
    int id;
    @Column(name = "published_at")
    Date publishedAt;
    @Column(name = "title")
    String title;
    @Column(name = "content")
    String content;
    @ManyToOne
    @JoinColumn(name = "author_id")
    Author author;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "article_category",
            joinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id"))
    List<Category> categories;
}
