package com.udacity.jwdnd.course1.cloudstorage.models.ui;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

/**
 * author: Heng Yu
 */
@Setter
@Getter
public class CredentialDto {
    @Positive
    private Integer credentialId;
    @NotBlank
    private String url;
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    private String plaintPassword;
}
