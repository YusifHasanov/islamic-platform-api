package com.msys.esm.api;
import com.msys.esm.business.concretes.ArticleService;
import com.msys.esm.core.dto.Request.Create.CreateArticle;
import com.msys.esm.core.dto.Request.Update.UpdateArticle;
import com.msys.esm.core.dto.Response.ArticleResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/articles")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ArticleController {
    ArticleService service;
    @GetMapping
    public ResponseEntity<List<ArticleResponse>> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleResponse> getById(@PathVariable int id) {
        return service.getById(id);
    }

    @PostMapping
    public ResponseEntity<CreateArticle> add(@Valid @RequestBody CreateArticle article) {
        return service.add(article);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateArticle> update(@Valid @RequestBody UpdateArticle article, @PathVariable int id) {
        return service.update(article, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ArticleResponse> delete(@PathVariable int id) {
        return service.delete(id);
    }
}
