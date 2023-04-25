package com.msys.esm.Controller;

import com.msys.esm.Service.Abstracts.IBookService;
import com.msys.esm.Core.DTO.Request.Create.CreateBook;
import com.msys.esm.Core.DTO.Request.Update.UpdateBook;
import com.msys.esm.Core.DTO.Response.BookResponse;
import com.msys.esm.Service.Concretes.BookService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
@FieldDefaults(makeFinal = true,level = AccessLevel.PRIVATE)
public class BookController {

    BookService service;

    @GetMapping
    public ResponseEntity<List<BookResponse>> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getById(@PathVariable int id) {
        return service.getById(id);
    }

    @PostMapping
    public ResponseEntity<CreateBook> add(@Valid @RequestBody CreateBook book) {
        System.out.println(book);
        return service.add(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateBook> update(@Valid @RequestBody UpdateBook book , @PathVariable int id) {
        return service.update(book,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookResponse> delete(@PathVariable int id) {
        return service.delete(id);
    }

}