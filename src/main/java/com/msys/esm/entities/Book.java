package com.msys.esm.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "books")
public class Book   {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @NotBlank  @Size(max =50)
    @Column(name = "title")
    String title;
    @NotBlank
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    Author author;
}
