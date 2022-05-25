package com.udacity.jwdnd.course1.cloudstorage.utils;

import org.springframework.util.Base64Utils;

import java.security.SecureRandom;


/**
 * Common util provide some common utils
 * author: Heng Yu
 */
public class CommonUtil {

    private CommonUtil() {
    }


    /**
     * Generate the secure random string
     * @return the random string
     */
    public static String generateSecureString(){
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return Base64Utils.encodeToString(salt);

    }

}
