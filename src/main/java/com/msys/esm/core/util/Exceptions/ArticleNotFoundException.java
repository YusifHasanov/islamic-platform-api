package com.msys.esm.core.util.Exceptions;

import com.msys.esm.core.util.Exceptions.Global.NotFoundException;

public class ArticleNotFoundException extends NotFoundException {
    public ArticleNotFoundException(String message) {
        super(message);
    }
}
