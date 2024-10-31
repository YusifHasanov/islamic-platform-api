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

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "categories", uniqueConstraints = {@UniqueConstraint(columnNames = "name")})
public class Category {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    Integer parentId;

    @NotBlank
    @Convert(converter = TrimValidator.class)
    @Column(name = "name", unique = true)
    String name;

//    @ToStringExclude
//    @EqualsExclude
//    @HashCodeExclude
//    @JsonIgnore
//    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
//    Set<Question> questions;

    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Set<Article> articles;

//    @OneToMany(cascade = CascadeType.ALL, mappedBy="parent", fetch = FetchType.LAZY)
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    private Set<Category> children =new HashSet<>();

//    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
//    @JoinColumn(name = "parent_id")
//    @JsonIgnore
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    private Category parent;

}
