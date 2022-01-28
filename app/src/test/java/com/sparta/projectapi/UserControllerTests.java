package com.sparta.projectapi;

import com.sparta.projectapi.repositories.LoginRepository;
import com.sparta.projectapi.requests.RequestFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URISyntaxException;

public class UserControllerTests {
    private static String createUserResponse;
    private static String deleteUserResponse;
    private static String getUserResponse;

    @Autowired
    LoginRepository loginRepository;

    @Test
    @DisplayName("POST valid user response")
    public void getUserCreateResponse() throws IOException, URISyntaxException, InterruptedException {
        createUserResponse = RequestFactory.postUser();
        Assertions.assertTrue(createUserResponse.equals("New User was created. Register a login before using the services"));
    }

    @Test
    @DisplayName("DELETE user valid response")
    public void deleteUserResponse() throws IOException, URISyntaxException, InterruptedException {
        //This works and is ready for live demo
        deleteUserResponse = RequestFactory.deleteUser(3, "Yefri51","SnzidBnJ7ZstsRuwqBUMbZepjBn5VIAWhIG4Efjr");
        Assertions.assertTrue(deleteUserResponse.equals("User and credentials have been deleted"));
    }

    @Test
    @DisplayName("DELETE user invalid token response")
    public void deleteUserErrorResponse() throws IOException, URISyntaxException, InterruptedException {
        deleteUserResponse = RequestFactory.deleteUser(2, "test","SnzidBnJ7ZstsRuwqBUMbZepjBn5VIAWhIG4Efjr");
        Assertions.assertTrue(deleteUserResponse.equals("You are not authorized for this page, Check your username or token and try again."));
    }

    @Test
    @DisplayName("GET user valid response")
    public void getUserResponse() throws IOException, URISyntaxException, InterruptedException {
        getUserResponse = RequestFactory.getUser(3);
        Assertions.assertTrue(getUserResponse.equals("id=3, name='Ignas'"));
    }

    @Test
    @DisplayName("GET user invalid response")
    public void getUserErrorResponse() throws IOException, URISyntaxException, InterruptedException {
        getUserResponse = RequestFactory.getUser(1);
        Assertions.assertTrue(getUserResponse.equals("No user was found. Perhaps you entered the wrong id?"));
    }

}
