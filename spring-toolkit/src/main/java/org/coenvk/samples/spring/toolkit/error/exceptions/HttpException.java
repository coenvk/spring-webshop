package org.coenvk.samples.spring.toolkit.error.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Generic Exception class which can be used to set certain HTTP status codes along with response objects.
 */
public class HttpException extends ResponseStatusException {
    private Object response;

    public HttpException() {
        super(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public HttpException(HttpStatus status) {
        super(status);
    }

    public HttpException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public HttpException(HttpStatus status, String reason, Object response) {
        super(status, reason);
        this.response = response;
    }

    public HttpException(HttpStatus status, String reason, Throwable cause, Object response) {
        super(status, reason, cause);
        this.response = response;
    }

    public Object getResponse() {
        return response;
    }
}