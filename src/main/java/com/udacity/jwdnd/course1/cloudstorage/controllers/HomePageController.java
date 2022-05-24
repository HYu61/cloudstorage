package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.exceptions.UserNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        User loginUser = userService.getUserByUsername(authentication.getName()).orElseThrow(() -> new UserNotFoundException("User not exists"));
        Integer userId = loginUser.getUserId();

        model.addAttribute("credentials", credentialService.getAllCredentials(userId));
        model.addAttribute("username", authentication.getName());
        return "/home";
    }


}
