package com.msys.esm.Repository;

import com.msys.esm.Model.Article;
import com.msys.esm.Model.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

    @Query(value = "SELECT category_id FROM public.article_category WHERE article_id = ?1", nativeQuery = true)
    Set<Integer> getCategoriesIdsById(int id);


    @Query(value = "SELECT id FROM public.articles WHERE author_id = ?1", nativeQuery = true)
    Set<Integer> getArticleIdsByAuthorId(int id);

    boolean existsArticleByTitle(String title);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query(value = "DELETE FROM public.article_category WHERE article_id = ?1", nativeQuery = true)
    void deleteArticle(int id);

}
