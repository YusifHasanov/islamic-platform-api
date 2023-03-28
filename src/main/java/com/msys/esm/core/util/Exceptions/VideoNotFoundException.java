package com.msys.esm.core.util.Exceptions;

import com.msys.esm.core.util.Exceptions.Global.NotFoundException;

public class VideoNotFoundException extends NotFoundException {
    public VideoNotFoundException(String message) {
        super(message);
    }
}
