package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.exceptions.UserNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.models.ui.CredentialDto;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
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
    private final UserService userService;

    public HomePageController(CredentialService credentialService, UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @GetMapping
    public String displayHomePage(Authentication authentication, Model model) {
        User loginUser = userService.getUserByUsername(authentication.getName()).orElseThrow(() -> new UserNotFoundException());
        Integer userId = loginUser.getUserId();

        List<CredentialDto> credentialDtoList = credentialService.getAllCredentials(userId).stream()
                .map(c->{
                    CredentialDto credentialDto = new CredentialDto();
                    BeanUtils.copyProperties(c,credentialDto);
                    // add decryption password to the dto
                    credentialDto.setPlaintPassword(credentialService.decryptionPassword(c,credentialDto));
                    return credentialDto;
                }).collect(Collectors.toList());

        model.addAttribute("credentials", credentialDtoList);
        model.addAttribute("username", authentication.getName());
        return "/home";
    }


}
