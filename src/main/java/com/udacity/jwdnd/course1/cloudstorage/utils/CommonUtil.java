package com.udacity.jwdnd.course1.cloudstorage.utils;

import org.springframework.util.Base64Utils;

import java.security.SecureRandom;
import java.text.DecimalFormat;


/**
 * Common util provide some common utils
 * author: Heng Yu
 */
public class CommonUtil {
    private static final double KB = 1024.0;
    private static final double MB = 1024 * 1024.0;
    private static final double GB = 1024 * 1024 * 1024.0;

    private CommonUtil() {
    }


    /**
     * Generate the secure random string
     *
     * @return the random string
     */
    public static String generateSecureString() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return Base64Utils.encodeToString(salt);

    }

    /**
     * Convert the file size from byte to kb or mb or gb
     *
     * @param fileSize
     * @return
     */
    public static String convertFileSize(long fileSize) {
        DecimalFormat df = new DecimalFormat("#.##");

        String result;
        float size = 0;
        if (fileSize < KB) {
            result = fileSize + " B";
        } else if (fileSize < MB) {
            result = df.format(fileSize / KB) +" KB";

        } else if (fileSize < GB) {
            result = df.format(fileSize / MB) +" MB";
        } else {
            result = df.format(fileSize / GB) +" GB";

        }
        return result;
    }


}
