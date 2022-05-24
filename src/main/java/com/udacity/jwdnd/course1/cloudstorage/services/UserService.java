package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.utils.CommonUtil;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;

/**
 * author: Heng Yu
 */
@Service
public class UserService {
    private final UserMapper userMapper;
    private final HashService hashService;

    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    public boolean isUserExist(String username){
        return userMapper.selectUserByUsername(username).isPresent();
    }

    public Optional<User> getUserByUsername(String username){
        if(username == null){
            throw new IllegalArgumentException("username is required");
        }
        return userMapper.selectUserByUsername(username);
    }

    public Integer addUser(User user){
        // generate slat
        String salt = CommonUtil.generateSecureString();
        user.setSalt(salt);

        // hash the password
        user.setPassword(hashService.getHashedValue(user.getPassword(),salt));

        // insert user into db
        return userMapper.insertUser(user);
    }
}
