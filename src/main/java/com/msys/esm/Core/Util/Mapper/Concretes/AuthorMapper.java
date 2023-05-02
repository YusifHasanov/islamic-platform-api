package com.msys.esm.Core.Util.Mapper.Concretes;

import com.msys.esm.Core.DTO.Request.Update.UpdateAuthor;
import com.msys.esm.Core.DTO.Response.AuthorResponse;
import com.msys.esm.Core.Util.Mapper.Abstracts.IAuthorMapper;
import com.msys.esm.Model.Author;
import com.msys.esm.Model.Book;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class AuthorMapper implements IAuthorMapper {
    @Override
    public void mapToAuthor(UpdateAuthor updateAuthor, Author author) {
        if (updateAuthor.getName() != null) author.setName(updateAuthor.getName());
        else updateAuthor.setName(author.getName());
        if (updateAuthor.getImage() != null) author.setImage(updateAuthor.getImage());
        else updateAuthor.setImage(author.getImage());
    }

    @Override
    public AuthorResponse mapToAuthorResponse(Author author) {
        AuthorResponse authorResponse = new AuthorResponse();
        authorResponse.setId(author.getId());
        authorResponse.setName(author.getName());
        authorResponse.setImage(author.getImage());
        authorResponse.setBooks(author.getBooks().stream()
                .map(Book::getId).collect(Collectors.toSet()));
        return authorResponse;
    }
}
