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
    private final QuestionService questionService;

    @GetMapping

    public ResponseEntity<List<QuestionResponse>> getAll() {
        return questionService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponse> getById(@PathVariable int id) {
        return questionService.getById(id);
    }

    @PostMapping
    public ResponseEntity<CreateQuestion> add(@Valid @RequestBody CreateQuestion Question) {
        return questionService.add(Question);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateQuestion> update(@Valid @RequestBody UpdateQuestion Question ,@PathVariable int id) {
        return questionService.update(Question,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<QuestionResponse> delete(@PathVariable int id) {
        return questionService.delete(id);
    }
}
