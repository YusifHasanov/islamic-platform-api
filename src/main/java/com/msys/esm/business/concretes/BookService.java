package com.msys.esm.business.concretes;

import com.msys.esm.business.abstracts.IBookService;
import com.msys.esm.core.dto.Request.Create.CreateBook;
import com.msys.esm.core.dto.Request.Update.UpdateBook;
import com.msys.esm.core.dto.Response.BookResponse;
import com.msys.esm.core.util.Exceptions.BookNotFoundException;
import com.msys.esm.core.util.mapper.concretes.ModelService;
import com.msys.esm.core.util.rules.CheckIds;
import com.msys.esm.dataAccess.BookRepository;
import com.msys.esm.entities.Book;
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
public class BookService implements IBookService {

    BookRepository repository;
    ModelService mapper;

    @Override
    public ResponseEntity<List<BookResponse>> getAll() {
        List<Book> Books = repository.findAll();
        List<BookResponse> responseBooks = Books.stream()
                .map(v -> mapper.forResponse().map(v, BookResponse.class)).toList();
        return ResponseEntity.ok(responseBooks);
    }

    @Override
    public ResponseEntity<BookResponse> getById(int id) {
        Book Book = repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
        return ResponseEntity.ok(mapper.forResponse().map(Book, BookResponse.class));
    }

    @Override
    public ResponseEntity<CreateBook> add(CreateBook Book) {
        Book mappedBook = mapper.forRequest().map(Book, Book.class);
        repository.save(mappedBook);
        return ResponseEntity.status(HttpStatus.CREATED).body(Book);
    }

    @Override
    public ResponseEntity<BookResponse> delete(int id) {
        Book Book = repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
        repository.deleteById(id);
        BookResponse BookREsponse = mapper.forResponse().map(Book, BookResponse.class);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(BookREsponse);
    }

    @Override
    public ResponseEntity<UpdateBook> update(UpdateBook Book, int id) {
        Book findedBook = repository.findById(Book.getId())
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + Book.getId()));
        CheckIds.check(findedBook.getId(), id);
        repository.save(mapper.forRequest().map(Book, Book.class));
        return ResponseEntity.ok(Book);
    }


}
