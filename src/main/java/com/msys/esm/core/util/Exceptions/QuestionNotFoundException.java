package com.msys.esm.core.util.Exceptions;

import com.msys.esm.core.util.Exceptions.Global.NotFoundException;

public class QuestionNotFoundException extends NotFoundException {
    public QuestionNotFoundException(String message) {
        super(message);
    }
}
