package com.udacity.jwdnd.course1.cloudstorage.models;

import lombok.Getter;
import lombok.Setter;

/**
 * author: Heng Yu
 */
@Getter
@Setter
public class Note {
    private Integer noteId;
    private String noteTitle;
    private String noteDescription;
    private Integer userId;

}
