package com.msys.esm.business.abstracts;

import com.msys.esm.core.dto.Request.Create.CreateQuestion;
import com.msys.esm.core.dto.Request.Update.UpdateQuestion;
import com.msys.esm.core.dto.Response.QuestionResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IQuestionService {
    ResponseEntity<List<QuestionResponse>> getAll();
    ResponseEntity<QuestionResponse> getById(int id);
    ResponseEntity<CreateQuestion> add(CreateQuestion question);
    ResponseEntity<UpdateQuestion> update(UpdateQuestion question,int id);
    ResponseEntity<QuestionResponse> delete(int id);
}
