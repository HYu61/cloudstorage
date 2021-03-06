package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.models.ui.CredentialDto;
import com.udacity.jwdnd.course1.cloudstorage.models.ui.FileDto;
import com.udacity.jwdnd.course1.cloudstorage.models.ui.NoteDto;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

/**
 * author: Heng Yu
 */
@Controller
@RequestMapping("/home")
public class HomePageController {
    private final CredentialService credentialService;
    private final NoteService noteService;
    private final UserService userService;
    private final FileService fileService;

    public HomePageController(CredentialService credentialService, NoteService noteService, UserService userService, FileService fileService) {
        this.credentialService = credentialService;
        this.noteService = noteService;
        this.userService = userService;
        this.fileService = fileService;
    }

    @GetMapping
    public String displayHomePage(Authentication authentication, Model model) {
        User loginUser = userService.getUserByUsername(authentication.getName());
        Integer userId = loginUser.getUserId();

        // create a new list for credentials, include encrypted and plaint password
        List<CredentialDto> credentialDtoList = credentialService.getAllCredentials(userId).stream()
                .map(c->{
                    CredentialDto credentialDto = new CredentialDto();
                    BeanUtils.copyProperties(c,credentialDto);
                    // add decryption password to the dto
                    credentialDto.setPlaintPassword(credentialService.decryptionPassword(c,credentialDto));
                    return credentialDto;
                }).collect(Collectors.toList());

        // get noteList for display
        List<NoteDto> noteDtoList = noteService.getAllNotesByUserId(userId).stream()
                .map(n->{
                    NoteDto noteDto = new NoteDto();
                    BeanUtils.copyProperties(n, noteDto);
                    return noteDto;
                }).collect(Collectors.toList());

        // get fileList for display
        List<FileDto> fileDtoList = fileService.getFiles(userId).stream()
                .map(f->{
                    FileDto fileDto = new FileDto();
                    BeanUtils.copyProperties(f, fileDto);
                    return fileDto;
                }).collect(Collectors.toList());

        model.addAttribute("credentials", credentialDtoList);
        model.addAttribute("notes", noteDtoList);
        model.addAttribute("files", fileDtoList);

        model.addAttribute("username", authentication.getName());
        return "/home";
    }


}
