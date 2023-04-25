package com.msys.esm.Core.Util.Exceptions;

import com.msys.esm.Core.Util.Exceptions.Global.NotFoundException;

public class BookNotFoundException extends NotFoundException {
    public BookNotFoundException(String message) {
        super(message);
    }
}
