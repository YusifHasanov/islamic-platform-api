package com.msys.esm.Controller;

import com.msys.esm.Service.Abstracts.ICategoryService;
import com.msys.esm.Core.DTO.Request.Create.CreateCategory;
import com.msys.esm.Core.DTO.Request.Update.UpdateCategory;
import com.msys.esm.Core.DTO.Response.CategoryResponse;
import com.msys.esm.Core.Util.Messages.MessageAndStatusCode;
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

    ICategoryService service;

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getById(@PathVariable int id) {
        return service.getById(id);
    }

    @PostMapping
    public ResponseEntity<CreateCategory> add(@Valid @RequestBody CreateCategory category) {
        return service.add(category);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateCategory> update(@Valid @RequestBody UpdateCategory category , @PathVariable int id) {
        return service.update(category,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {return service.delete(id);}

}