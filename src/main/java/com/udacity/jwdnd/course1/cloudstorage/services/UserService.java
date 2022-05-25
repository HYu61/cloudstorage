package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mappers.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.models.ui.UserDto;
import com.udacity.jwdnd.course1.cloudstorage.utils.CommonUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

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

    /**
     * Check if the user exists according to the username
     * @param username
     * @return true if exists; otherwise return flase
     */
    public boolean isUserExist(String username){
        return userMapper.selectUserByUsername(username).isPresent();
    }

    /**
     * Get the user according to the username
     * @param username
     * @return the Optional<User> or Optional.empty id the username is invalid
     */
    public Optional<User> getUserByUsername(String username){
        return userMapper.selectUserByUsername(username);
    }

    /**
     * Get the userId according to the logged in username
     * @param username
     * @return the userid or null if the username is invalid
     */
    public Integer getLoginUserId(String username){
        return userMapper.selectUserIdByUsername(username);
    }

    /**
     * Insert user into DB
     * @param userDto
     * @return the userid of the inserted user
     */
    public Integer addUser(UserDto userDto){
        User user = new User();
        BeanUtils.copyProperties(userDto, user);

        // generate slat
        String salt = CommonUtil.generateSecureString();
        user.setSalt(salt);

        // hash the password
        user.setPassword(hashService.getHashedValue(user.getPassword(),salt));

        // insert user into db
        return userMapper.insertUser(user);
    }
}
