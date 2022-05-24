package com.udacity.jwdnd.course1.cloudstorage.exceptions;

import org.springframework.http.HttpStatus;

/**
 * author: Heng Yu
 */
public class UserNotFoundException extends AppException {
    public UserNotFoundException(String message) {
        super(message);
        this.httpStatus = HttpStatus.NOT_FOUND;
    }
}
