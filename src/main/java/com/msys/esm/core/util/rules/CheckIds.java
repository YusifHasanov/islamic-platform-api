package com.msys.esm.core.util.rules;


import java.util.Objects;

public interface CheckIds {
    static void check(int  entityId, int id) {
        if (entityId != id)
            throw new IllegalArgumentException("Id mismatch between request and entity");
    }
    static void checkForPlayListOrVideo(String  entityId, String id) {
        if (!Objects.equals(entityId, id))
            throw new IllegalArgumentException("Id mismatch between request and entity");
    }
}
