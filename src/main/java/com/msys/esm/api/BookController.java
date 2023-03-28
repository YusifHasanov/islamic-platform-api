package com.msys.esm.api;

import com.msys.esm.business.abstracts.IBookService;
import com.msys.esm.core.dto.Request.Create.CreateBook;
import com.msys.esm.core.dto.Request.Update.UpdateBook;
import com.msys.esm.core.dto.Response.BookResponse;
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
    IBookService bookService;

    @GetMapping
    public ResponseEntity<List<BookResponse>> getAll() {
        return bookService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getById(@PathVariable int id) {
        return bookService.getById(id);
    }

    @PostMapping
    public ResponseEntity<CreateBook> add(@Valid @RequestBody CreateBook Book) {
        return bookService.add(Book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateBook> add(@Valid @RequestBody UpdateBook Book , @PathVariable int id) {
        return bookService.update(Book,id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookResponse> delete(@PathVariable int id) {
        return bookService.delete(id);
    }

}