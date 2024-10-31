package com.msys.esm.Service.Abstracts;

import com.msys.esm.Core.DTO.Request.Create.CreateBook;
import com.msys.esm.Core.DTO.Request.Update.UpdateBook;
import com.msys.esm.Core.DTO.Response.BookResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IBookService {
    ResponseEntity<List<BookResponse>> getAll();
    ResponseEntity<BookResponse> getById(int id);
    ResponseEntity<?> add(CreateBook book);
    ResponseEntity<BookResponse> delete(int id);
    ResponseEntity<UpdateBook> update(UpdateBook  book, int id);
}
