package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.exceptions.AppException;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.models.ui.UserForm;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * author: Heng Yu
 */
@Controller
@RequestMapping("/signup")
public class SignUpPageController {
    private final UserService userService;

    public SignUpPageController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String displaySignUpPage(UserForm userForm) {
        return "signup";
    }

    @PostMapping
    public String registerUser(@ModelAttribute("userForm") @Valid UserForm userForm,
                               Model model, RedirectAttributes redirectAttributes) {

        // check if the username already exists
        if (userService.isUserExist(userForm.getUsername())) {
            model.addAttribute("msg", "Username already exists!");
            return "signup";
        }

        // add user into db
        User user = new User();
        BeanUtils.copyProperties(userForm, user);
        Integer result = userService.addUser(user);

        // if has problem send the error message
        if (result == null) {
            throw new AppException("Can not insert the user");
        }

        // if sign up successfully redirect to the login page
        redirectAttributes.addFlashAttribute("signupSuccess", true);
        return "redirect:login";


    }
}
