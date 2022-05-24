package com.udacity.jwdnd.course1.cloudstorage.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * author: Heng Yu
 */
@Getter
@Setter
public class AppException extends RuntimeException{
//    protected String message = super.getMessage();
    protected HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public AppException(String message) {
        super(message);
    }
}
