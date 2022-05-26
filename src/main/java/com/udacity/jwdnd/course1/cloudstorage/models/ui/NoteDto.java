package com.udacity.jwdnd.course1.cloudstorage.models.ui;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;

/**
 * author: Heng Yu
 */
@Getter
@Setter
public class NoteDto {
    private Integer noteId;
    private String noteTitle;
    private String noteDescription;

}
