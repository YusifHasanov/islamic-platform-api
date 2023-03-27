package com.example.api.core.util.Exceptions;

import com.example.api.core.util.Exceptions.Global.NotFoundException;

public class VideoNotFoundException extends NotFoundException {
    public VideoNotFoundException(String message) {
        super(message);
    }
}
