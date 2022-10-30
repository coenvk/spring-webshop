package org.coenvk.samples.spring.toolkit.error.exceptions;

import lombok.NonNull;
import org.springframework.http.HttpStatus;

public class IllegalDatabaseState extends HttpException {
    public IllegalDatabaseState() {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "Model is incorrectly stored in the database.");
    }

    public IllegalDatabaseState(@NonNull String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }

    public <I> IllegalDatabaseState(I id) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, "Model with id " + id + " is incorrectly stored in the database.");
    }

    public <T, I> IllegalDatabaseState(Class<T> clazz, I id) {
        super(HttpStatus.INTERNAL_SERVER_ERROR,
                "Model " + clazz.getSimpleName() + " with id " + id + " is incorrectly stored in the database.");
    }
}
