package com.lwz.hospitalsystem.utils;

import org.springframework.util.DigestUtils;

public class Md5Password {
    public  static String getMd5Password(String password) {
        String salt="1a2b3c4d5e6f7g";
        for (int i = 0; i < 3; i++) {
            password= DigestUtils.md5DigestAsHex((salt+password+salt).getBytes()).toUpperCase();
        }
        return password;
    }
}
