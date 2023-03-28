package com.msys.esm.api;
import com.msys.esm.business.concretes.QuestionService;
import com.msys.esm.core.dto.Request.Create.CreateQuestion;
import com.msys.esm.core.dto.Request.Update.UpdateQuestion;
import com.msys.esm.core.dto.Response.QuestionResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService service;

    @GetMapping

    public ResponseEntity<List<QuestionResponse>> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponse> getById(@PathVariable int id) {
        return service.getById(id);
    }

    @PostMapping
    public ResponseEntity<CreateQuestion> add(@Valid @RequestBody CreateQuestion Question) {
        return service.add(Question);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateQuestion> update(@Valid @RequestBody UpdateQuestion Question ,@PathVariable int id) {
        return service.update(Question,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<QuestionResponse> delete(@PathVariable int id) {
        return service.delete(id);
    }
}
