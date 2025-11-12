package com.oop.naingue.demo5.utils;

import org.mindrot.jbcrypt.BCrypt;

public class PassHasher {
    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    public static boolean verifyPass(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}
