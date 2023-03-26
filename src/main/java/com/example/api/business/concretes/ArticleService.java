package com.example.api.business.concretes;
import com.example.api.business.abstracts.IArticleService;
import com.example.api.core.dto.POST.ArticlePost;
import com.example.api.core.dto.PUT.ArticlePut;
import com.example.api.core.util.mapper.concretes.ModelService;
import com.example.api.dataAccess.ArticleRepository;
import com.example.api.entities.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService implements IArticleService {
    private final ArticleRepository articleRepository;
    private final ModelService mapper;

    @Override
    public ResponseEntity<List<Article>> getAll() {
        List<Article> articles = articleRepository.findAll();
        if (articles.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(articles);
    }

    @Override
    public ResponseEntity<Article> getById(int id) {
        Article article = articleRepository.findById(id).orElse(null);
        if (article == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(article);
    }

    @Override
    public ResponseEntity<ArticlePost> add(ArticlePost article) {
        Article mappedArticle = mapper.forRequest().map(article, Article.class);
        articleRepository.save(mappedArticle);
        return ResponseEntity.ok(article);
    }

    @Override
    public ResponseEntity<ArticlePut> update(ArticlePut article) {
        Article updateArticle = articleRepository.findById(article.getId()).orElse(null);
        if (updateArticle == null)
            return ResponseEntity.notFound().build();
        articleRepository.save(mapper.forRequest().map(article, Article.class));
        return ResponseEntity.ok(article);
    }

    @Override
    public ResponseEntity<Article> delete(int id) {
        Article article = articleRepository.findById(id).orElse(null);
        if (article == null)
            return ResponseEntity.notFound().build();
        articleRepository.delete(article);
        return ResponseEntity.ok(article);
    }
}
