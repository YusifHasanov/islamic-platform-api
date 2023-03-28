package com.msys.esm.api;

import com.msys.esm.business.concretes.ArticleService;
import com.msys.esm.core.dto.Request.Create.CreateArticle;
import com.msys.esm.core.dto.Request.Update.UpdateArticle;
import com.msys.esm.core.dto.Response.ArticleResponse;
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
    public ResponseEntity<UpdateArticle> update(@Valid @RequestBody UpdateArticle article ,@PathVariable int id) {
        return articleService.update(article,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ArticleResponse> delete(@PathVariable int id) {
        return articleService.delete(id);
    }
}
