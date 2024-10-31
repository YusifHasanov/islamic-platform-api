package com.msys.esm.Model;

import com.msys.esm.Core.Util.Validators.Concretes.TrimValidator;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "articles", uniqueConstraints = {@UniqueConstraint(columnNames = "title")})
public class Article {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "published_at")
    @Convert(converter = TrimValidator.class)
    String publishedAt;

    @NotBlank
    @Convert(converter = TrimValidator.class)
    @Column(name = "title", unique = true)
    String title;

    @NotBlank
    @Column(name = "content", columnDefinition = "text")
    @Convert(converter = TrimValidator.class)
    String content;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "author_id")
//    Author author;

//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinTable(name = "article_category",
//            joinColumns = @JoinColumn(name = "article_id", referencedColumnName = "id"),
//            inverseJoinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"))
//    Set<Category> categories;

}
