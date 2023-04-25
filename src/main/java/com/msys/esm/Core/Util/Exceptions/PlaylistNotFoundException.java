package com.msys.esm.Core.Util.Exceptions;

import com.msys.esm.Core.Util.Exceptions.Global.NotFoundException;

public class PlaylistNotFoundException extends NotFoundException {
    public PlaylistNotFoundException(String message) {
        super(message);
    }
}
