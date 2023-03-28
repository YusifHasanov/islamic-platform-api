package com.msys.esm.api;

import com.msys.esm.business.abstracts.ICategoryService;
import com.msys.esm.core.dto.Request.Create.CreateCategory;
import com.msys.esm.core.dto.Request.Update.UpdateArticle;
import com.msys.esm.core.dto.Request.Update.UpdateCategory;
import com.msys.esm.core.dto.Response.CategoryResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class CategoryController {
    ICategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAll() {
        return categoryService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getById(@PathVariable int id) {
        return categoryService.getById(id);
    }

    @PostMapping
    public ResponseEntity<CreateCategory> add(@Valid @RequestBody CreateCategory category) {
        return categoryService.add(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateCategory> add(@Valid @RequestBody UpdateCategory category , @PathVariable int id) {
        return categoryService.update(category,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryResponse> delete(@PathVariable int id) {
        return categoryService.delete(id);
    }

}