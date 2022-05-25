package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * author: Heng Yu
 */
@Service
public class AuthenticationService implements AuthenticationProvider {

    private final UserService userService;
    private final HashService hashService;

    public AuthenticationService(UserService userService, HashService hashService) {
        this.userService = userService;
        this.hashService = hashService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        User loginUser = userService.getUserByUsername(username).orElse(null);
        if(loginUser !=null && hashService.getHashedValue(password,loginUser.getSalt()).equals(loginUser.getPassword())){
            return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
        }

        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
