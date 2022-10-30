package org.coenvk.samples.spring.toolkit.error.exceptions;

import lombok.NonNull;
import org.springframework.http.HttpStatus;

/**
 * Exception to signal that a certain model could not be found in the database.
 */
public class ModelNotFound extends HttpException {
    public ModelNotFound() {
        super(HttpStatus.NOT_FOUND, "Could not find model");
    }

    public ModelNotFound(@NonNull String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

    public <I> ModelNotFound(I id) {
        super(HttpStatus.NOT_FOUND, "Could not find model with id " + id);
    }

    public <T, I> ModelNotFound(Class<T> clazz, I id) {
        super(HttpStatus.NOT_FOUND, "Could not find " + clazz.getSimpleName() + " with id " + id);
    }
}
