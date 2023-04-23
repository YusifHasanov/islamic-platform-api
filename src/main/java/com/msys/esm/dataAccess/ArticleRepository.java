package com.msys.esm.dataAccess;

import com.msys.esm.entities.Article;
import com.msys.esm.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface ArticleRepository extends JpaRepository<Article, Integer> {
    @Query(value = "SELECT category_id FROM public.article_category WHERE article_id = ?1", nativeQuery = true)
    Set<Integer> getCategoriesById(int id);


}
