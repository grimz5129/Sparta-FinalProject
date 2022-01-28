package com.sparta.projectapi;

import com.sparta.projectapi.requests.RequestFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;

public class LoginControllerTests {
    private String checkLoginResponse;

    @Test
    @DisplayName("POST valid login response")
    public void checkLoginResponse() throws IOException, URISyntaxException, InterruptedException {
        checkLoginResponse = RequestFactory.loginRequest("/login/check","POST","login.json");
        Assertions.assertTrue(checkLoginResponse.contains("User Authorized. Current Access Token is "));
    }

    @Test
    @DisplayName("POST Bad login response")
    public void checkBadLoginResponse() throws IOException, URISyntaxException, InterruptedException {
        checkLoginResponse = RequestFactory.loginRequest("/login/check","POST","badLogin.json");
        Assertions.assertTrue(checkLoginResponse.equals("Username was not found. Perhaps you forgot to register first?"));
    }

    @Test
    @DisplayName("POST incorrect login response")
    public void checkWrongPasswordResponse() throws IOException, URISyntaxException, InterruptedException {
        checkLoginResponse = RequestFactory.loginRequest("/login/check","POST","badPassword.json");
        Assertions.assertTrue(checkLoginResponse.equals("Password does not match. Try again."));
    }



}
