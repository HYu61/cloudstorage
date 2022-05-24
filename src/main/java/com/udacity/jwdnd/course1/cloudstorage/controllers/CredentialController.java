package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.exceptions.UserNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.models.ui.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * author: Heng Yu
 */
@Controller
@RequestMapping("/credential")
public class CredentialController {
    private final CredentialService credentialService;
    private final UserService userService;

    public CredentialController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }



    @PostMapping
    public String addCredential(@Valid CredentialForm credentialForm, Authentication authentication, Model model) {

        Credential newCredential = new Credential();
        BeanUtils.copyProperties(credentialForm, newCredential);

        String loginUsername = authentication.getName();


        // set userId to the credential model, if the user is not exists, throw exception.
        userService.getUserByUsername(loginUsername).ifPresentOrElse(loginUser -> {
            newCredential.setUserId(loginUser.getUserId());
        }, () -> {
            throw new UserNotFoundException("User not exists!");
        });


        credentialService.addCredential(newCredential);

        return "redirect:/home";
    }
}
