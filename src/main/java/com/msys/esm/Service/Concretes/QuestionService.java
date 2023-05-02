package com.msys.esm.Service.Concretes;
import com.msys.esm.Core.Util.Mapper.Abstracts.QuestionMapper;
import com.msys.esm.Repository.CategoryRepository;
import com.msys.esm.Service.Abstracts.IQuestionService;
import com.msys.esm.Core.DTO.Request.Create.CreateQuestion;
import com.msys.esm.Core.DTO.Request.Update.UpdateQuestion;
import com.msys.esm.Core.DTO.Response.QuestionResponse;
import com.msys.esm.Core.Util.Exceptions.QuestionNotFoundException;
import com.msys.esm.Core.Util.Mapper.Concretes.ModelService;
import com.msys.esm.Core.Util.Rules.CheckIds;
import com.msys.esm.Repository.QuestionRepository;
import com.msys.esm.Model.Question;
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
    com.msys.esm.Core.Util.Mapper.Concretes.QuestionMapper questionMapper;

    @Override
    public ResponseEntity<List<QuestionResponse>> getAll() {

        List<Question> questions = repository.findAll();

        List<QuestionResponse> responseQuestions = questions.stream()
                .map(questionMapper::mapToResponse)
                .toList();

        return ResponseEntity.ok(responseQuestions);

    }

    @Override
    public ResponseEntity<QuestionResponse> getById(int id) {

        Question question = getQuestion(id);

        return ResponseEntity.ok(questionMapper.mapToResponse(question));

    }

    @Override
    public ResponseEntity<CreateQuestion> add(CreateQuestion question) {

        repository.save(questionMapper.mapCreateToQuestion(question));

        return ResponseEntity.status(HttpStatus.CREATED).body(question);

    }

    @Override
    public ResponseEntity<UpdateQuestion> update(UpdateQuestion question, int id) {

        Question updateQuestion = getQuestion(id);

        CheckIds.check(updateQuestion.getId(), id);

        repository.save(questionMapper.mapUpdateToQuestion(updateQuestion, question));

        return ResponseEntity.ok(question);

    }

    @Override
    public ResponseEntity<QuestionResponse> delete(int id) {

        Question question = getQuestion(id);


        repository.delete(question);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    private Question getQuestion(int id) {

        return repository.findById(id).orElseThrow(() ->
                new QuestionNotFoundException("question not found with id: " + id));

    }

}
