package com.msys.esm.dataAccess;
import com.msys.esm.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AuthorRepository extends JpaRepository<Author, Integer> {
}
