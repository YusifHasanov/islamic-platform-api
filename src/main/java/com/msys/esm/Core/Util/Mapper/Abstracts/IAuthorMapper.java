package com.msys.esm.Core.Util.Mapper.Abstracts;

import com.msys.esm.Core.DTO.Request.Update.UpdateAuthor;
import com.msys.esm.Core.DTO.Response.AuthorResponse;
import com.msys.esm.Model.Author;

public interface IAuthorMapper {
    void mapToAuthor(UpdateAuthor updateAuthor, Author author);
    AuthorResponse mapToAuthorResponse(Author author);
}
