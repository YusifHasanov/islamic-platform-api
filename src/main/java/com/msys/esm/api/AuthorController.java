package com.msys.esm.api;

import com.msys.esm.business.concretes.AuthorService;
import com.msys.esm.core.dto.Request.Create.CreateAuthor;
import com.msys.esm.core.dto.Request.Update.UpdateAuthor;
import com.msys.esm.core.dto.Response.AuthorResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping

    public ResponseEntity<List<AuthorResponse>> getAll() {
        return authorService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponse> getById(@PathVariable int id) {
        return authorService.getById(id);
    }

    @PostMapping
    public ResponseEntity<CreateAuthor> add(@Valid @RequestBody CreateAuthor Author) {
        return authorService.add(Author);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateAuthor> update(@Valid @RequestBody UpdateAuthor Author ,@PathVariable int id) {
        return authorService.update(Author,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AuthorResponse> delete(@PathVariable int id) {
        return authorService.delete(id);
    }
}
