package com.msys.esm.Core.Util.Exceptions;

import com.msys.esm.Core.Util.Exceptions.Global.NotFoundException;

public class StatisticNotFoundException extends NotFoundException {
    public StatisticNotFoundException(String message) {
        super(message);
    }
}
