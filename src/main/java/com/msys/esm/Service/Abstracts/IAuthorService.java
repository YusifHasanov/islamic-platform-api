package com.msys.esm.Service.Abstracts;

import com.msys.esm.Core.DTO.Request.Create.CreateAuthor;
import com.msys.esm.Core.DTO.Request.Update.UpdateAuthor;
import com.msys.esm.Core.DTO.Response.AuthorResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IAuthorService {
    ResponseEntity<List<AuthorResponse>> getAll();
    ResponseEntity<AuthorResponse> getById(int id);
    ResponseEntity<CreateAuthor> add(CreateAuthor author);
    ResponseEntity<UpdateAuthor> update(UpdateAuthor author,int id);
    ResponseEntity<AuthorResponse> delete(int id);
}
