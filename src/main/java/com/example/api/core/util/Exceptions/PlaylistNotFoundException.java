package com.example.api.core.util.Exceptions;

import com.example.api.core.util.Exceptions.Global.NotFoundException;

public class PlaylistNotFoundException extends NotFoundException {
    public PlaylistNotFoundException(String message) {
        super(message);
    }
}
