package com.udacity.jwdnd.course1.cloudstorage.models;

import com.udacity.jwdnd.course1.cloudstorage.annotations.Password;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

/**
 * author: Heng Yu
 */

@Getter
@Setter
public class User {
    private Integer userId;

    private String username;

    private String salt;

    private String password;

    private String firstName;

    private String lastName;

}
