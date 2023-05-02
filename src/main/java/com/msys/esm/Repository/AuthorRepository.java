package com.msys.esm.Repository;
import com.msys.esm.Model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AuthorRepository extends JpaRepository<Author, Integer> {
    boolean existsAuthorById(int id);
}
