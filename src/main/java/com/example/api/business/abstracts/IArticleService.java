package com.example.api.business.abstracts;

import com.example.api.core.dto.POST.ArticlePost;
import com.example.api.core.dto.PUT.ArticlePut;
import com.example.api.entities.Article;
import org.springframework.http.ResponseEntity;

import java.util.List;

    public interface IArticleService {
        ResponseEntity<List<Article>> getAll();
        ResponseEntity<Article> getById(int id);
        ResponseEntity<ArticlePost> add(ArticlePost article);
        ResponseEntity<ArticlePut> update(ArticlePut article);
        ResponseEntity<Article> delete(int id);
}
