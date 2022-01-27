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

    @Autowired
    LoginRepository loginRepository;


    @Test
    @DisplayName("GET valid user response")
    public void getUserCreateResponse() throws IOException, URISyntaxException, InterruptedException {
        createUserResponse = RequestFactory.postUser();
        Assertions.assertTrue(createUserResponse.equals("New User was created. Register a login before using the services"));
    }

    @Test
    @DisplayName("DELETE user valid response")
    public void deleteUserResponse() throws IOException, URISyntaxException, InterruptedException {
        deleteUserResponse = RequestFactory.deleteUser(2, "TestUser2","CekNycAEXYyZtVRCqgGwbCndBBIj88evhDZ5pvX6");
        Assertions.assertTrue(deleteUserResponse.equals("User and credentials have been deleted"));
    }

    @Test
    @DisplayName("DELETE user valid response")
    public void deleteUserHTTPResponse() throws IOException, URISyntaxException, InterruptedException {
//        deleteUserResponse = RequestFactory.deleteUser(1, userRepository.findAllById(1).get(2));
        Assertions.assertTrue(deleteUserResponse.equals("User and credentials have been deleted"));
    }

}
