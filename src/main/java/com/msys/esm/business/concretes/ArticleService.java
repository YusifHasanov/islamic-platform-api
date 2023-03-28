package com.msys.esm.business.concretes;

import com.msys.esm.business.abstracts.IArticleService;
import com.msys.esm.core.dto.Request.Create.CreateArticle;
import com.msys.esm.core.dto.Request.Update.UpdateArticle;
import com.msys.esm.core.dto.Response.ArticleResponse;
import com.msys.esm.core.util.Exceptions.ArticleNotFoundException;
import com.msys.esm.core.util.mapper.concretes.ModelService;
import com.msys.esm.core.util.rules.CheckIds;
import com.msys.esm.dataAccess.ArticleRepository;
import com.msys.esm.entities.Article;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ArticleService implements IArticleService {
    ArticleRepository articleRepository;
    ModelService mapper;

    @Override
    public ResponseEntity<List<ArticleResponse>> getAll() {
        List<Article> articles = articleRepository.findAll();
        if (articles.isEmpty())
            return ResponseEntity.noContent().build();
        List<ArticleResponse> responseArticles = articles
                .stream()
                .map(a -> mapper.forResponse().map(a, ArticleResponse.class)).toList();
        return ResponseEntity.ok(responseArticles);
    }

    @Override
    public ResponseEntity<ArticleResponse> getById(int id) {
        Article article = articleRepository.findById(id).orElseThrow(() ->
                new ArticleNotFoundException("Article not found with id: " + id));
        if (article == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(mapper.forResponse().map(article, ArticleResponse.class));
    }

    @Override
    public ResponseEntity<CreateArticle> add(CreateArticle article) {
        Article mappedArticle = mapper.forRequest().map(article, Article.class);
        articleRepository.save(mappedArticle);
        return ResponseEntity.status(HttpStatus.CREATED).body(article);
    }

    @Override
    public ResponseEntity<UpdateArticle> update(UpdateArticle article, int id) {
        Article updateArticle = articleRepository.findById(article.getId())
                .orElseThrow(() -> new ArticleNotFoundException("Article not found with id: " + article.getId()));
        CheckIds.check(updateArticle.getId(), id);
        articleRepository.save(mapper.forRequest().map(article, Article.class));
        return ResponseEntity.ok(article);
    }

    @Override
    public ResponseEntity<ArticleResponse> delete(int id) {
        Article article = articleRepository.findById(id)
                .orElseThrow(() -> new ArticleNotFoundException("Article not found with id: " + id));

        articleRepository.delete(article);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(mapper.forResponse().map(article, ArticleResponse.class));
    }
}
