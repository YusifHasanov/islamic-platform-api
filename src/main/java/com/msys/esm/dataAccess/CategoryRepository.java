package com.msys.esm.dataAccess;

import com.msys.esm.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    @Query(value = "SELECT article_id FROM public.article_category WHERE category_id = ?1", nativeQuery = true)
    Set<Integer> getArticlesById(int id);

    boolean existsCategoryById(int id);

}
