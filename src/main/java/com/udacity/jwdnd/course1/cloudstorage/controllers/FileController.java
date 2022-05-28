package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.exceptions.FileException;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.utils.CommonUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * author: Heng Yu
 */
@Controller
@RequestMapping("/files")
public class FileController {
    private final UserService userService;
    private final FileService fileService;

    public FileController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @PostMapping
    public String storeNewFile(@RequestParam(name = "fileUpload") MultipartFile multipartFile,
                               Authentication authentication, RedirectAttributes redirectAttributes) {
        String loginUser = authentication.getName();
        if (multipartFile.isEmpty()) {
            throw new FileException("No such file or the file is empty!");
        }

        String savedFileName = fileService.storeFile(multipartFile, loginUser);
        redirectAttributes.addFlashAttribute("fileMsg",
                String.format("File %s is uploaded successfully!", savedFileName));
        return "redirect:/home";
    }

    @GetMapping(value = "/fileId", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody()
    public ResponseEntity<byte[]> accessFile(@RequestParam Integer fileId, Authentication authentication) {
        Integer loginUserId = userService.getLoginUserId(authentication.getName());

        File file = fileService.loadFile(fileId,loginUserId);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getFileName())
                .body(file.getFileData());
    }

    @GetMapping("/delete/fileId")
    public String deleteFile(@RequestParam Integer fileId, Authentication authentication,
                             RedirectAttributes redirectAttributes) {
        if (fileId == null) {
            throw new FileException("No such file");
        }
        Integer loginUserId = userService.getLoginUserId(authentication.getName());
        String deletedFileName = fileService.deleteFile(fileId, loginUserId);
        redirectAttributes.addFlashAttribute("fileMsg",
                String.format("File %s is removed successfully!", deletedFileName));

        return "redirect:/home";
    }
}
