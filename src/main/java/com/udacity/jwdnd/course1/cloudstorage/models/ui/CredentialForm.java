package com.udacity.jwdnd.course1.cloudstorage.models.ui;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

/**
 * author: Heng Yu
 */
@Setter
@Getter
public class CredentialForm {
    @NotBlank
    private String url;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
