package com.msys.esm.Service.Concretes;

import com.msys.esm.Repository.ArticleRepository;
import com.msys.esm.Repository.BookRepository;
import com.msys.esm.Service.Abstracts.IAuthorService;
import com.msys.esm.Core.DTO.Request.Create.CreateAuthor;
import com.msys.esm.Core.DTO.Request.Update.UpdateAuthor;
import com.msys.esm.Core.DTO.Response.AuthorResponse;
import com.msys.esm.Core.Util.Exceptions.AuthorNotFoundException;
import com.msys.esm.Core.Util.Mapper.Concretes.ModelService;
import com.msys.esm.Core.Util.Rules.CheckIds;
import com.msys.esm.Repository.AuthorRepository;
import com.msys.esm.Model.Author;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.hibernate.Hibernate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AuthorService implements IAuthorService {

    AuthorRepository repository;
    BookRepository bookRepository;
    ArticleRepository articleRepository;
    ModelService mapper;

    @Override
    public ResponseEntity<List<AuthorResponse>> getAll() {

        List<Author> authors = repository.findAll();

        if (authors.isEmpty()) return ResponseEntity.noContent().build();

        List<AuthorResponse> responseAuthors = authors
                .stream()
                .map(a -> mapper.setIntegerTosetBook().map(a, AuthorResponse.class)).toList();

        return ResponseEntity.ok(responseAuthors);

    }

    @Override
    public ResponseEntity<AuthorResponse> getById(int id) {

        Author author = repository.findById(id).orElseThrow(() ->
                new AuthorNotFoundException("author not found with id: " + id));

        return author == null ? ResponseEntity.notFound().build() :
                ResponseEntity.ok(mapper.setIntegerTosetBook().map(author, AuthorResponse.class));

    }

    @Override
    public ResponseEntity<CreateAuthor> add(CreateAuthor author) {

        Author mappedAuthor = mapper.forRequest().map(author, Author.class);

        repository.save(mappedAuthor);

        return ResponseEntity.status(HttpStatus.CREATED).body(author);

    }

    @Override
    public ResponseEntity<UpdateAuthor> update(UpdateAuthor author, int id) {

        Author updateAuthor = repository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Author not found with id: " + id));

        CheckIds.check(updateAuthor.getId(), id);

        if (author.getName() != null) updateAuthor.setName(author.getName());
        if (author.getImage() != null) updateAuthor.setImage(author.getImage());


        repository.save(updateAuthor);

        return ResponseEntity.ok(mapper.forResponse().map(updateAuthor, UpdateAuthor.class));

    }

    @Override
    public ResponseEntity<AuthorResponse> delete(int id) {

        Author author = repository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Author not found with id: " + id));

        articleRepository.getArticleIdsByAuthorId(id).forEach(articleRepository::deleteArticle);
        articleRepository.getArticleIdsByAuthorId(id).forEach(articleRepository::deleteById);


        Hibernate.initialize(author.getBooks());
        bookRepository.deleteByAuthorId(repository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Author not found with id: " + id)).getId());
        repository.delete(repository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("Author not found with id: " + id)));

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(mapper.forResponse().map(author, AuthorResponse.class));

    }
}
