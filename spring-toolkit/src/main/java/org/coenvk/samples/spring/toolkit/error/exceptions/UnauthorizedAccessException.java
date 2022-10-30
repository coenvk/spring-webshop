package org.coenvk.samples.spring.toolkit.error.exceptions;

import lombok.NonNull;
import org.springframework.http.HttpStatus;

public class UnauthorizedAccessException extends HttpException {
    public UnauthorizedAccessException() {
        this("User does not have access to assets");
    }

    public UnauthorizedAccessException(@NonNull String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
