package com.msys.esm.Core.Util.Mapper.Concretes;

import com.msys.esm.Core.DTO.Request.Create.CreateBook;
import com.msys.esm.Core.DTO.Request.Update.UpdateBook;
import com.msys.esm.Core.Util.Exceptions.AuthorNotFoundException;
import com.msys.esm.Core.Util.Exceptions.BookNotFoundException;
import com.msys.esm.Core.Util.Mapper.Abstracts.IBookMapper;
import com.msys.esm.Core.Util.Rules.CheckIds;
import com.msys.esm.Model.Author;
import com.msys.esm.Model.Book;
import com.msys.esm.Repository.AuthorRepository;
import com.msys.esm.Repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BookMapper implements IBookMapper {
    AuthorRepository authorRepository;
    BookRepository bookRepository;

    @Override
    public Book mapCreateBookToBook(CreateBook book) {
        Book newBook = new Book();
        newBook.setTitle(book.getTitle());
        newBook.setAuthor(authorRepository.findById(book.getAuthorId()).get());
        return newBook;
    }

    @Override
    public Book updateToBook(UpdateBook book, int id) {

        Book findedBook = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));

        CheckIds.check(findedBook.getId(), id);

        if (book.getTitle() != null) findedBook.setTitle(book.getTitle());
        else book.setTitle(findedBook.getTitle());

        if (book.getAuthorId() != 0) {
            Author author = authorRepository.findById(book.getAuthorId())
                    .orElseThrow(() -> new AuthorNotFoundException("Author not found with id: " + book.getAuthorId()));
            findedBook.setAuthor(author);
        } else book.setAuthorId(findedBook.getAuthor().getId());

        return findedBook;

    }
}
