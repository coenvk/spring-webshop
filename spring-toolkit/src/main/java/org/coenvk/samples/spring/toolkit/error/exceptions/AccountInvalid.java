package org.coenvk.samples.spring.toolkit.error.exceptions;

import lombok.NonNull;
import org.springframework.http.HttpStatus;

public class AccountInvalid extends HttpException {

    public AccountInvalid() {
        this("The database contains an invalid entry for this account.");
    }

    public AccountInvalid(@NonNull String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }

    public <I> AccountInvalid(I id) {
        super(HttpStatus.UNAUTHORIZED, "The database contains an invalid entry for account with id " + id + ".");
    }
}
