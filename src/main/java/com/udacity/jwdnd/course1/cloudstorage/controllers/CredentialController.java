package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.exceptions.CredentialException;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.ui.CredentialDto;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * author: Heng Yu
 */
@Controller
@RequestMapping("/credential")
public class CredentialController {
    private final CredentialService credentialService;

    public CredentialController(CredentialService credentialService) {
        this.credentialService = credentialService;
    }


    @PostMapping
    public String addOrEditCredential(@Valid CredentialDto credentialDto, Authentication authentication, RedirectAttributes redirectAttributes) {
        String loginUsername = authentication.getName();

        if (credentialDto.getCredentialId() == null) {
            // add credential into db
            credentialService.addCredential(credentialDto, loginUsername);
            redirectAttributes.addFlashAttribute("credMsg", String.format("Credential for %s is created!", credentialDto.getUrl()));
        } else {
            // update credential
            credentialService.modifyCredential(credentialDto, loginUsername);
            redirectAttributes.addFlashAttribute("credMsg", String.format("Credential for %s is edited!", credentialDto.getUrl()));

        }

        return "redirect:/home";
    }

    @GetMapping("/delete/credentialId")
    public String deleteCredential(@RequestParam Integer credentialId, Authentication authentication, RedirectAttributes redirectAttributes) {
        if (credentialId == null) {
            throw new CredentialException("No such credential");
        }

        Credential removedCred = credentialService.removeCredential(credentialId, authentication.getName());
        redirectAttributes.addFlashAttribute("credMsg", String.format("Credential: %s for %s is removed!", removedCred.getUsername(), removedCred.getUrl()));

        return "redirect:/home";
    }
}
