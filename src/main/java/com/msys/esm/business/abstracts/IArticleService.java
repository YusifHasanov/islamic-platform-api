package com.msys.esm.business.abstracts;

import com.msys.esm.core.dto.Request.Create.CreateArticle;
import com.msys.esm.core.dto.Request.Update.UpdateArticle;
import com.msys.esm.core.dto.Response.ArticleResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

    public interface IArticleService {
        ResponseEntity<List<ArticleResponse>> getAll();
        ResponseEntity<ArticleResponse> getById(int id);
        ResponseEntity<CreateArticle> add(CreateArticle article);
        ResponseEntity<UpdateArticle> update(UpdateArticle article,int id);
        ResponseEntity<ArticleResponse> delete(int id);
}
