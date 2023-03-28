package com.msys.esm.core.util.Exceptions;

import com.msys.esm.core.util.Exceptions.Global.NotFoundException;

public class CategoryNotFoundException extends NotFoundException {
    public CategoryNotFoundException(String message) {
        super(message);
    }
}
