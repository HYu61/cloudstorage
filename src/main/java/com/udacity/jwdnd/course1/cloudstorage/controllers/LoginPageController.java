package com.udacity.jwdnd.course1.cloudstorage.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



/**
 * author: Heng Yu
 */
@Controller
@RequestMapping("/login")
public class LoginPageController {

    @GetMapping
    public String displayLoginPage(){
        return "login";
    }

}
