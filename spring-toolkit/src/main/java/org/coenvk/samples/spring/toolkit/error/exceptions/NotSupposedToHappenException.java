package org.coenvk.samples.spring.toolkit.error.exceptions;

import lombok.NonNull;

public class NotSupposedToHappenException extends RuntimeException {
    public NotSupposedToHappenException() {
    }

    public NotSupposedToHappenException(@NonNull String message) {
        super(message);
    }

    public NotSupposedToHappenException(@NonNull String message, Throwable cause) {
        super(message, cause);
    }

    public NotSupposedToHappenException(Throwable cause) {
        super(cause);
    }
}
