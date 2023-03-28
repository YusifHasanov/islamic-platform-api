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
        List<Question> questions = repository.findAll();
        List<QuestionResponse> responseQuestions = questions.stream().map(
                a -> mapper.forResponse().map(a, QuestionResponse.class)).toList();
        return ResponseEntity.ok(responseQuestions);
    }

    @Override
    public ResponseEntity<QuestionResponse> getById(int id) {
        Question question = repository.findById(id).orElseThrow(() ->
                new QuestionNotFoundException("question not found with id: " + id));
        return ResponseEntity.ok(mapper.forResponse().map(question, QuestionResponse.class));
    }

    @Override
    public ResponseEntity<CreateQuestion> add(CreateQuestion question) {
        Question mappedQuestion = mapper.forRequest().map(question, Question.class);
        repository.save(mappedQuestion);
        return ResponseEntity.status(HttpStatus.CREATED).body(question);
    }

    @Override
    public ResponseEntity<UpdateQuestion> update(UpdateQuestion question, int id) {
        Question updateQuestion = repository.findById(question.getId())
                .orElseThrow(() -> new QuestionNotFoundException("Question not found with id: " + question.getId()));
        CheckIds.check(updateQuestion.getId(), id);
        repository.save(mapper.forRequest().map(question, Question.class));
        return ResponseEntity.ok(question);
    }

    @Override
    public ResponseEntity<QuestionResponse> delete(int id) {
        Question question = repository.findById(id)
                .orElseThrow(() -> new QuestionNotFoundException("question not found with id: " + id));
        repository.delete(question);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(mapper.forResponse().map(question, QuestionResponse.class));
    }
}
