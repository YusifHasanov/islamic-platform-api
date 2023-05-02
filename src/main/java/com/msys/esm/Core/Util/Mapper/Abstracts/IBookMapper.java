package com.msys.esm.Core.Util.Mapper.Abstracts;

import com.msys.esm.Core.DTO.Request.Create.CreateBook;
import com.msys.esm.Core.DTO.Request.Update.UpdateBook;
import com.msys.esm.Model.Book;

public interface IBookMapper {

    Book mapCreateBookToBook(CreateBook book);

    Book updateToBook(UpdateBook book, int id);
}
