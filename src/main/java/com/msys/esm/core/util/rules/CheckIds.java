package com.msys.esm.core.util.rules;


public interface CheckIds {
    static void check(int  entityId, int id) {
        if (entityId != id)
            throw new IllegalArgumentException("Id mismatch between request and entity");
    }
}
