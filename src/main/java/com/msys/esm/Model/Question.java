package com.msys.esm.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.msys.esm.Core.Util.Validators.Concretes.TrimValidator;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.builder.EqualsExclude;
import org.apache.commons.lang3.builder.HashCodeExclude;
import org.apache.commons.lang3.builder.ToStringExclude;

import java.util.Set;

@Getter
@Setter
@ToString(exclude = {"categories"},doNotUseGetters = true)
@EqualsAndHashCode(exclude = {"categories"},doNotUseGetters = true)
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "questions", uniqueConstraints = {@UniqueConstraint(columnNames = "question")})
public class Question  {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @NotBlank
    @Convert(converter = TrimValidator.class)
    @Column(name = "question")
    String question;

    @NotBlank
    @Convert(converter = TrimValidator.class)
    @Column(name = "answer")
    String answer;

    @ToStringExclude
    @EqualsExclude
    @HashCodeExclude
    @ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JsonIgnore
    @JoinTable(name = "question_category",
            joinColumns = @JoinColumn(name = "category_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "question_id", referencedColumnName = "id"))
    Set<Category> categories;

}
