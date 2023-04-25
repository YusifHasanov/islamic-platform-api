package com.msys.esm.Service.Abstracts;

import com.msys.esm.Core.DTO.Request.Create.CreateArticle;
import com.msys.esm.Core.DTO.Request.Update.UpdateArticle;
import com.msys.esm.Core.DTO.Response.ArticleResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

    public interface IArticleService {
        ResponseEntity<List<ArticleResponse>> getAll();
        ResponseEntity<ArticleResponse> getById(int id);
        ResponseEntity<CreateArticle> add(CreateArticle article);
        ResponseEntity<UpdateArticle> update(UpdateArticle article,int id);
        ResponseEntity<ArticleResponse> delete(int id);
}
