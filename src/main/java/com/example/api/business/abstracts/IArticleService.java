package com.example.api.business.abstracts;

import com.example.api.core.dto.Request.Create.CreateArticle;
import com.example.api.core.dto.Request.Update.UpdateArticle;
import com.example.api.core.dto.Response.ArticleResponse;
import com.example.api.entities.Article;
import org.springframework.http.ResponseEntity;

import java.util.List;

    public interface IArticleService {
        ResponseEntity<List<ArticleResponse>> getAll();
        ResponseEntity<ArticleResponse> getById(int id);
        ResponseEntity<CreateArticle> add(CreateArticle article);
        ResponseEntity<UpdateArticle> update(com.example.api.core.dto.Request.Update.UpdateArticle article);
        ResponseEntity<ArticleResponse> delete(int id);
}
