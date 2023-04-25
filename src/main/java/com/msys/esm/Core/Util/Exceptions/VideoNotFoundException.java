package com.msys.esm.Core.Util.Exceptions;

import com.msys.esm.Core.Util.Exceptions.Global.NotFoundException;

public class VideoNotFoundException extends NotFoundException {
    public VideoNotFoundException(String message) {
        super(message);
    }
}
