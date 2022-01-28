package com.sparta.projectapi;

import java.util.regex.Pattern;

public class ValidationMethods {

    public static boolean validateUsername(String username) {
        if (username.length() > 0 && username.length() <= 128) {
            return Pattern.matches("[a-zA-Z0-9]+", username);
        }
        return false;
    }

    public static boolean validatePassword(String password) {
        return password.length() >= 8 && Pattern.matches("(.)*(\\d)*(.)*", password) && Pattern.matches("(.)*([A-Z])*(.)*", password) && Pattern.matches("(.)*([^\\w])*(.)*", password);
    }

}
