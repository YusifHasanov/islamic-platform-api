package com.msys.esm.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.msys.esm.Core.Util.Validators.Concretes.TrimValidator;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.builder.EqualsExclude;
import org.apache.commons.lang3.builder.HashCodeExclude;

@Setter
@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "books", uniqueConstraints = {@UniqueConstraint(columnNames = "title")})
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @NotBlank @Size(max = 50)
    @Convert(converter = TrimValidator.class)
    @Column(name = "title", unique = true)
    String title;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JsonIgnore
    @HashCodeExclude
    @ToString.Exclude
    @EqualsExclude
    @JoinColumn(name = "author_id")
    Author author;

}
