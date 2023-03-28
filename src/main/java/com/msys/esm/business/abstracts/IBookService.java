package com.msys.esm.business.abstracts;

import com.msys.esm.core.dto.Request.Create.CreateBook;
import com.msys.esm.core.dto.Request.Update.UpdateBook;
import com.msys.esm.core.dto.Response.BookResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IBookService {
    ResponseEntity<List<BookResponse>> getAll();
    ResponseEntity<BookResponse> getById(int id);
    ResponseEntity<CreateBook> add(CreateBook Book);
    ResponseEntity<BookResponse> delete(int id);
    ResponseEntity<UpdateBook> update(UpdateBook  Book, int id);
}
