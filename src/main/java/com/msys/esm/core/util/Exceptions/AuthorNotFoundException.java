package com.msys.esm.core.util.Exceptions;

import com.msys.esm.core.util.Exceptions.Global.NotFoundException;

public class AuthorNotFoundException extends NotFoundException {
    public AuthorNotFoundException(String message) {
        super(message);
    }
}
