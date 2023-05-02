package com.msys.esm.Controller;
import com.msys.esm.Service.Abstracts.IQuestionService;
import com.msys.esm.Service.Concretes.QuestionService;
import com.msys.esm.Core.DTO.Request.Create.CreateQuestion;
import com.msys.esm.Core.DTO.Request.Update.UpdateQuestion;
import com.msys.esm.Core.DTO.Response.QuestionResponse;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/questions")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class QuestionController {
    IQuestionService service;

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
