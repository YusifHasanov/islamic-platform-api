package com.msys.esm.Core.Util.Exceptions;

import com.msys.esm.Core.Util.Exceptions.Global.NotFoundException;

public class ArticleNotFoundException extends NotFoundException {
    public ArticleNotFoundException(String message) {
        super(message);
    }
}
