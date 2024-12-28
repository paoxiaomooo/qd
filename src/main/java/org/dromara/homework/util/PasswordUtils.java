package org.dromara.homework.util;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {

    // 使用BCrypt加密密码
    public static String encryptPassword(String password) {
//        // 使用 BCryptPasswordEncoder 进行加密
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(password);  // 返回加密后的密码

    }

    // 验证密码是否匹配
    public static boolean verifyPassword(String rawPassword, String encryptedPassword) {

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(rawPassword, encryptedPassword);  // 比较输入的密码和加密的密码
    }
}
