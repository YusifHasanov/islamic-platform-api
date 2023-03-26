package com.example.api.api;

import com.example.api.business.concretes.ArticleService;
import com.example.api.core.dto.POST.ArticlePost;
import com.example.api.core.dto.PUT.ArticlePut;
import com.example.api.dataAccess.ArticleRepository;
import com.example.api.entities.Article;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
public class ArticleController {
    private final ArticleService articleService;

    @GetMapping

    public ResponseEntity<List<Article>> getAll() {
        return articleService.getAll();
    }
    @GetMapping("/{id}")
    public ResponseEntity<Article> getById(@PathVariable int id) {
        return articleService.getById(id);
    }

    @PostMapping
    public ResponseEntity<ArticlePost> add(@Valid @RequestBody ArticlePost article) {
        return articleService.add(article);
    }
    @PutMapping("/{id}")
    public ResponseEntity<ArticlePut> update(@Valid @RequestBody ArticlePut article , @PathVariable int id) {
        return articleService.update(article);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Article> delete(@PathVariable int id) {
        return articleService.delete(id);
    }
}
