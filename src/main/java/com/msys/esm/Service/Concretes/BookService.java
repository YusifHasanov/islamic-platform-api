package com.msys.esm.Service.Concretes;

import com.msys.esm.Core.Util.Exceptions.AuthorNotFoundException;
import com.msys.esm.Model.Author;
import com.msys.esm.Repository.AuthorRepository;
import com.msys.esm.Service.Abstracts.IBookService;
import com.msys.esm.Core.DTO.Request.Create.CreateBook;
import com.msys.esm.Core.DTO.Request.Update.UpdateBook;
import com.msys.esm.Core.DTO.Response.BookResponse;
import com.msys.esm.Core.Util.Exceptions.BookNotFoundException;
import com.msys.esm.Core.Util.Mapper.Concretes.ModelService;
import com.msys.esm.Core.Util.Rules.CheckIds;
import com.msys.esm.Repository.BookRepository;
import com.msys.esm.Model.Book;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class BookService implements IBookService {

    BookRepository repository;
    AuthorRepository authorRepository;
    ModelService mapper;

    @Override
    public ResponseEntity<List<BookResponse>> getAll() {

        List<Book> books = repository.findAll();
        List<BookResponse> responseBooks = books.stream()
                .map(v -> mapper.forResponse().map(v, BookResponse.class)).toList();

        return ResponseEntity.ok(responseBooks);

    }

    @Override
    public ResponseEntity<BookResponse> getById(int id) {
        Book book = repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
        return ResponseEntity.ok(mapper.forResponse().map(book, BookResponse.class));
    }

    @Override
    public ResponseEntity<CreateBook> add(CreateBook book) {

        Author author = authorRepository.findById(book.getAuthorId())
                .orElseThrow(() -> new BookNotFoundException("Author not found with id: " + book.getAuthorId()));
        Set<Book> books = author.getBooks() != null ? author.getBooks() : new HashSet<>();
        Book createdBook = new Book();

        createdBook.setTitle(book.getTitle());
        createdBook.setAuthor(author);

        books.add(createdBook);
        author.setBooks(books);

        repository.save(createdBook);
        authorRepository.save(author);

        return ResponseEntity.status(HttpStatus.CREATED).body(book);

    }

    @Override
    public ResponseEntity<BookResponse> delete(int id) {
        Book book = repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
        repository.deleteById(id);
        BookResponse BookREsponse = mapper.forResponse().map(book, BookResponse.class);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(BookREsponse);
    }

    @Override
    public ResponseEntity<UpdateBook> update(UpdateBook book, int id) {
        Book findedBook = repository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
        CheckIds.check(findedBook.getId(), id);

        if (book.getTitle() != null) findedBook.setTitle(book.getTitle());
        else book.setTitle(findedBook.getTitle());
        if (book.getAuthorId() != 0) {
            Author author = authorRepository.findById(book.getAuthorId())
                    .orElseThrow(() -> new AuthorNotFoundException("Author not found with id: " + book.getAuthorId()));
            findedBook.setAuthor(author);
        }else{
            book.setAuthorId(findedBook.getAuthor().getId());
        }

        repository.save(findedBook);

        return ResponseEntity.ok(book);
    }


}
