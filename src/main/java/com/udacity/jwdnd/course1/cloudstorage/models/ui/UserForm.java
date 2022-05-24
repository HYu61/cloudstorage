package com.udacity.jwdnd.course1.cloudstorage.models.ui;

import com.udacity.jwdnd.course1.cloudstorage.annotations.Password;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * author: Heng Yu
 */

@Getter
@Setter
public class UserForm {
    @NotBlank(message = "Username is required!")
    private String username;

//    @Password
    private String password;

    @NotBlank(message = "First Name is required!")
    private String firstName;

    @NotBlank(message = "Last Name is required!")
    private String lastName;
}
