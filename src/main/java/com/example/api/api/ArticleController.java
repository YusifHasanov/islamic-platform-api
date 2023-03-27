package com.example.api.api;

import com.example.api.business.concretes.ArticleService;
import com.example.api.core.dto.Request.Create.CreateArticle;
import com.example.api.core.dto.Request.Update.UpdateArticle;
import com.example.api.core.dto.Response.ArticleResponse;
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

    public ResponseEntity<List<ArticleResponse>> getAll() {
        return articleService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponse> getById(@PathVariable int id) {
        return articleService.getById(id);
    }

    @PostMapping
    public ResponseEntity<CreateArticle> add(@Valid @RequestBody CreateArticle article) {
        return articleService.add(article);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateArticle> update(@Valid @RequestBody UpdateArticle article) {
        return articleService.update(article);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ArticleResponse> delete(@PathVariable int id) {
        return articleService.delete(id);
    }
}
