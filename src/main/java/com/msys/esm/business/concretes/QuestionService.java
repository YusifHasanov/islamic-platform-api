package com.msys.esm.business.concretes;
import com.msys.esm.business.abstracts.IQuestionService;
import com.msys.esm.core.dto.Request.Create.CreateQuestion;
import com.msys.esm.core.dto.Request.Update.UpdateQuestion;
import com.msys.esm.core.dto.Response.QuestionResponse;
import com.msys.esm.core.util.Exceptions.QuestionNotFoundException;
import com.msys.esm.core.util.mapper.concretes.ModelService;
import com.msys.esm.core.util.rules.CheckIds;
import com.msys.esm.dataAccess.QuestionRepository;
import com.msys.esm.entities.Question;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class QuestionService implements IQuestionService {
    QuestionRepository repository;
    ModelService mapper;

    @Override
    public ResponseEntity<List<QuestionResponse>> getAll() {
        List<Question> Questions = repository.findAll();
        if (Questions.isEmpty())
            return ResponseEntity.noContent().build();
        List<QuestionResponse> responseQuestions = Questions
                .stream()
                .map(a -> mapper.forResponse().map(a, QuestionResponse.class)).toList();
        return ResponseEntity.ok(responseQuestions);
    }

    @Override
    public ResponseEntity<QuestionResponse> getById(int id) {
        Question Question = repository.findById(id).orElseThrow(() ->
                new QuestionNotFoundException("Question not found with id: " + id));
        if (Question == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(mapper.forResponse().map(Question, QuestionResponse.class));
    }

    @Override
    public ResponseEntity<CreateQuestion> add(CreateQuestion Question) {
        Question mappedQuestion = mapper.forRequest().map(Question, Question.class);
        repository.save(mappedQuestion);
        return ResponseEntity.status(HttpStatus.CREATED).body(Question);
    }

    @Override
    public ResponseEntity<UpdateQuestion> update(UpdateQuestion Question, int id) {
        Question updateQuestion = repository.findById(Question.getId())
                .orElseThrow(() -> new QuestionNotFoundException("Question not found with id: " + Question.getId()));
        CheckIds.check(updateQuestion.getId(), id);
        repository.save(mapper.forRequest().map(Question, Question.class));
        return ResponseEntity.ok(Question);
    }

    @Override
    public ResponseEntity<QuestionResponse> delete(int id) {
        Question Question = repository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException("Question not found with id: " + id));

        repository.delete(Question);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(mapper.forResponse().map(Question, QuestionResponse.class));
    }
}
