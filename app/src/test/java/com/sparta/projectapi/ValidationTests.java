package com.sparta.projectapi;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ValidationTests {

    @DisplayName("Testing valid usernames")
    @ParameterizedTest
    @ValueSource(strings = {"Grimz5129", "MarkAlmighty","Bignas12", "NikosPapa8"})
    public void checkValidUsername(String strings){
        Assertions.assertTrue(ValidationMethods.validateUsername(strings));
    }

    @DisplayName("Testing valid Passwords")
    @ParameterizedTest
    @ValueSource(strings = {"Password123", "Th1s1s@nly","LOKIJUHYGTFRDESWAQ", "QWERTYUIOP"})
    public void checkValidPasswords(String strings){
        Assertions.assertTrue(ValidationMethods.validatePassword(strings));
    }

}
