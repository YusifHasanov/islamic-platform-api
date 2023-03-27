package com.example.api.core.util.Exceptions;

import com.example.api.core.util.Exceptions.Global.NotFoundException;

public class ArticleNotFoundException extends NotFoundException {
    public ArticleNotFoundException(String message) {
        super(message);
    }
}
