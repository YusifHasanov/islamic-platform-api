package com.msys.esm.business.concretes;
import com.msys.esm.business.abstracts.IAuthorService;
import com.msys.esm.core.dto.Request.Create.CreateAuthor;
import com.msys.esm.core.dto.Request.Update.UpdateAuthor;
import com.msys.esm.core.dto.Response.AuthorResponse;
import com.msys.esm.core.util.Exceptions.AuthorNotFoundException;
import com.msys.esm.core.util.mapper.concretes.ModelService;
import com.msys.esm.core.util.rules.CheckIds;
import com.msys.esm.dataAccess.AuthorRepository;
import com.msys.esm.entities.Author;
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
public class AuthorService implements IAuthorService {
    AuthorRepository repository;
    ModelService mapper;

    @Override
    public ResponseEntity<List<AuthorResponse>> getAll() {
        List<Author> authors = repository.findAll();
        if (authors.isEmpty())
            return ResponseEntity.noContent().build();
        List<AuthorResponse> responseAuthors = authors
                .stream()
                .map(a -> mapper.forResponse().map(a, AuthorResponse.class)).toList();
        return ResponseEntity.ok(responseAuthors);
    }

    @Override
    public ResponseEntity<AuthorResponse> getById(int id) {
        Author author = repository.findById(id).orElseThrow(() ->
                new AuthorNotFoundException("author not found with id: " + id));
        if (author == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(mapper.forResponse().map(author, AuthorResponse.class));
    }

    @Override
    public ResponseEntity<CreateAuthor> add(CreateAuthor author) {
        Author mappedAuthor = mapper.forRequest().map(author, Author.class);
        repository.save(mappedAuthor);
        return ResponseEntity.status(HttpStatus.CREATED).body(author);
    }

    @Override
    public ResponseEntity<UpdateAuthor> update(UpdateAuthor author, int id) {
        Author updateAuthor = repository.findById(author.getId())
                .orElseThrow(() -> new AuthorNotFoundException("Author not found with id: " + author.getId()));
        CheckIds.check(updateAuthor.getId(), id);
        repository.save(mapper.forRequest().map(author, Author.class));
        return ResponseEntity.ok(author);
    }

    @Override
    public ResponseEntity<AuthorResponse> delete(int id) {
        Author author = repository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException("author not found with id: " + id));

        repository.delete(author);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(mapper.forResponse().map(author, AuthorResponse.class));
    }
}
