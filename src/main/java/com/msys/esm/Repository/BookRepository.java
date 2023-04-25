package com.msys.esm.Repository;

import com.msys.esm.Model.Book;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book,Integer> {

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("delete from Book b where b.author.id = ?1")
    void deleteByAuthorId(int authorId);
}
