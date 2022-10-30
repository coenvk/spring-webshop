package org.coenvk.samples.spring.toolkit.error.exceptions;

import lombok.NonNull;
import org.springframework.http.HttpStatus;

public class ModelImmutable extends HttpException {
    public ModelImmutable() {
        super(HttpStatus.METHOD_NOT_ALLOWED, "Cannot complete request since model is immutable.");
    }

    public ModelImmutable(@NonNull String message) {
        super(HttpStatus.METHOD_NOT_ALLOWED, message);
    }

    public <I> ModelImmutable(I id) {
        super(HttpStatus.METHOD_NOT_ALLOWED, "Cannot complete request since model with id " + id + " is immutable.");
    }

    public <T, I> ModelImmutable(Class<T> clazz, I id) {
        super(HttpStatus.METHOD_NOT_ALLOWED,
                "Cannot complete request since " + clazz.getSimpleName() + " with id " + id + " is immutable.");
    }
}
