package com.udacity.jwdnd.course1.cloudstorage.models;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

/**
 * author: Heng Yu
 */
@Getter
@Setter
public class Credential {

    private Integer credentialId;
    private String url;
    private String username;
    private String key;
    private String password;
    private Integer userId;

}
