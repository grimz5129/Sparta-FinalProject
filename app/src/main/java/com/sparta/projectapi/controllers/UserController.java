package com.sparta.projectapi.controllers;

import com.sparta.projectapi.entities.User;
import com.sparta.projectapi.repositories.LoginRepository;
import com.sparta.projectapi.repositories.UserRepository;
import com.sparta.projectapi.services.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

@RestController
public class UserController {

    @Autowired
    AuthorizationService authorizationService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    LoginRepository loginRepository;

    @GetMapping(value="/user/get/{id}")
    public ResponseEntity<String> getUser(@PathVariable int id){
        if(userRepository.existsById(id)){
            return new ResponseEntity<>((userRepository.getById(id).toString()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(("No user was found. Perhaps you entered the wrong id?"), HttpStatus.OK);
        }
    }

    @PostMapping("/user/create")
    public ResponseEntity<String> createNewUser(@RequestBody User newUser) {
        if(newUser != null) {
            if (validateUsername(newUser.getName())){
                userRepository.save(newUser);
                return new ResponseEntity<>("New User was created. Register a login before using the services", HttpStatus.CREATED);
            } else
                return new ResponseEntity<>("The name given contained illegal characters. Names may only contain letters, hyphens and spaces", HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>("There was an error creating a new User, check the format of your JSON body", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<String> deleteUser(@RequestHeader("Authorization") String usernameAndAuthToken, @PathVariable int id) {
        String[] headerParts = usernameAndAuthToken.split(" ");
        if (authorizationService.checkValidToken(headerParts[1], headerParts[2])) {
            userRepository.deleteById(id);
            loginRepository.deleteById(id);
            return new ResponseEntity<>("User and credentials have been deleted", HttpStatus.OK);
        } else
            return new ResponseEntity<>("You are not authorized for this page, Check your username or token and try again.", HttpStatus.UNAUTHORIZED);
    }

    private boolean validateUsername(String username){
        return Pattern.matches("[-a-zA-Z]*$", username);
    }
}

