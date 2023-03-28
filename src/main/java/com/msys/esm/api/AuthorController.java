package com.msys.esm.api;

import com.msys.esm.business.concretes.AuthorService;
import com.msys.esm.core.dto.Request.Create.CreateAuthor;
import com.msys.esm.core.dto.Request.Update.UpdateAuthor;
import com.msys.esm.core.dto.Response.AuthorResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true , level = AccessLevel.PRIVATE)
public class AuthorController {
     AuthorService service;

    @GetMapping

    public ResponseEntity<List<AuthorResponse>> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorResponse> getById(@PathVariable int id) {
        return service.getById(id);
    }

    @PostMapping
    public ResponseEntity<CreateAuthor> add(@Valid @RequestBody CreateAuthor Author) {
        return service.add(Author);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateAuthor> update(@Valid @RequestBody UpdateAuthor Author ,@PathVariable int id) {
        return service.update(Author,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AuthorResponse> delete(@PathVariable int id) {
        return service.delete(id);
    }
}
