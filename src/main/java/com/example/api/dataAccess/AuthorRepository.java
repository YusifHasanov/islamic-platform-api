package com.example.api.dataAccess;
import com.example.api.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
