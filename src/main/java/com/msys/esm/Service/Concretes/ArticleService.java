package com.msys.esm.Service.Concretes;

import com.msys.esm.Core.DTO.Request.Create.CreateArticle;
import com.msys.esm.Core.DTO.Request.Update.UpdateArticle;
import com.msys.esm.Core.DTO.Response.ArticleResponse;
import com.msys.esm.Core.Util.Exceptions.ArticleNotFoundException;
import com.msys.esm.Core.Util.Mapper.Concretes.ArticleMapper;
import com.msys.esm.Core.Util.Rules.CheckIds;
import com.msys.esm.Model.Article;
import com.msys.esm.Repository.ArticleRepository;
import com.msys.esm.Service.Abstracts.IArticleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ArticleService implements IArticleService {

    ArticleRepository articleRepository;
    ArticleMapper articleMapper;

    @Override
    public ResponseEntity<List<ArticleResponse>> getAll() {
        return ResponseEntity.ok(articleMapper.mapToArticleResponseList());
    }

    @Override
    public ResponseEntity<ArticleResponse> getById(int id) {

        Article article = getArticleById(id);


        return ResponseEntity.ok(articleMapper.mapToArticleResponse(article));

    }

    @Override
    public ResponseEntity<CreateArticle> add(CreateArticle article) {

        articleRepository.save(articleMapper.mapCreateAticleToArticle(article));

        return ResponseEntity.status(HttpStatus.CREATED).body(article);

    }
    @Override
    public ResponseEntity<UpdateArticle> update(UpdateArticle article, int id) {

        Article updateArticle = getArticleById(id);

        CheckIds.check(updateArticle.getId(), id);

        articleMapper.mapToUpdateArticle(article, updateArticle);

        articleRepository.save(updateArticle);

        return ResponseEntity.ok(article);

    }

    @Override
    public ResponseEntity<ArticleResponse> delete(int id) {

        Article article = getArticleById(id);

        Hibernate.initialize(article.getCategories());
        Hibernate.initialize(article.getAuthor().getBooks());

        articleRepository.deleteArticle(id);
        articleRepository.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(articleMapper.mapToArticleResponse(article));

    }

    private Article getArticleById(int id) {

        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException("Article not found with id: " + id));

        Hibernate.initialize(article.getCategories());

        return article;

    }

}