package com.udacity.jwdnd.course1.cloudstorage.models.ui;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

/**
 * author: Heng Yu
 */
@Getter
@Setter
public class FileDto {
    @Positive
    private Integer fileId;
    @NotBlank
    private String fileName;
    @NotBlank
    private String fileSize;
}
