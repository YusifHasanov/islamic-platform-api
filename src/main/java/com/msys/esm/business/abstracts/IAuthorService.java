package com.msys.esm.business.abstracts;

import com.msys.esm.core.dto.Request.Create.CreateAuthor;
import com.msys.esm.core.dto.Request.Update.UpdateAuthor;
import com.msys.esm.core.dto.Response.AuthorResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IAuthorService {
    ResponseEntity<List<AuthorResponse>> getAll();
    ResponseEntity<AuthorResponse> getById(int id);
    ResponseEntity<CreateAuthor> add(CreateAuthor Author);
    ResponseEntity<UpdateAuthor> update(UpdateAuthor Author,int id);
    ResponseEntity<AuthorResponse> delete(int id);
}
