package com.msys.esm.Service.Abstracts;

import com.msys.esm.Core.DTO.Request.Create.CreateQuestion;
import com.msys.esm.Core.DTO.Request.Update.UpdateQuestion;
import com.msys.esm.Core.DTO.Response.QuestionResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IQuestionService {
    ResponseEntity<List<QuestionResponse>> getAll();
    ResponseEntity<QuestionResponse> getById(int id);
    ResponseEntity<CreateQuestion> add(CreateQuestion question);
    ResponseEntity<UpdateQuestion> update(UpdateQuestion question,int id);
    ResponseEntity<QuestionResponse> delete(int id);
}
