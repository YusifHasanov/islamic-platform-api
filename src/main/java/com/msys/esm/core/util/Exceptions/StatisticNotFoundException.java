package com.msys.esm.core.util.Exceptions;

import com.msys.esm.core.util.Exceptions.Global.NotFoundException;

public class StatisticNotFoundException extends NotFoundException {
    public StatisticNotFoundException(String message) {
        super(message);
    }
}
