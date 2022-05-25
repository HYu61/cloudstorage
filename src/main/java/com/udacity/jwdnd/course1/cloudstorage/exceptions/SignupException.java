package com.udacity.jwdnd.course1.cloudstorage.exceptions;

import org.springframework.http.HttpStatus;

/**
 * author: Heng Yu
 */
public class SignupException extends AppException{
    public SignupException() {
        this.message="Can not sign up, please try it again!";
    }
}
