package com.sparta.projectapi.controllers;

import com.sparta.projectapi.entities.Login;
import com.sparta.projectapi.repositories.LoginRepository;
import com.sparta.projectapi.services.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.util.Random;
import java.util.regex.Pattern;

@RestController
public class LoginController {

    @Autowired
    LoginRepository loginRepository;
    @Autowired
    AuthorizationService authorizationService;
    private static final String validCharset = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    @PostMapping("/login/check")
    public ResponseEntity<String> checkLogin(@RequestBody Login loginToCheck){
        if (loginRepository.existsByUsername(loginToCheck.getUsername())){
            if (BCrypt.checkpw(loginToCheck.getPassword(), loginRepository.getByUsername(loginToCheck.getUsername()).getPassword())){
                return new ResponseEntity<>("User Authorized.", HttpStatus.ACCEPTED);
            } else return new ResponseEntity<>("Password does not match. Try again.", HttpStatus.UNAUTHORIZED);
        } else return new ResponseEntity<>("Username was not found. Perhaps you forgot to register first?", HttpStatus.NOT_ACCEPTABLE);
    }

    @PostMapping("/login/register")
    public ResponseEntity<String> registerLogin(@RequestBody Login newLogin){
        if (validateUsername(newLogin.getUsername())){
            if (!loginRepository.existsByUsername(newLogin.getUsername())) {
                if (validatePassword(newLogin.getPassword())) {
                    newLogin.setPassword(BCrypt.hashpw(newLogin.getPassword(), BCrypt.gensalt()));
                    newLogin.setCurrentToken(generateNewToken());
                    loginRepository.save(newLogin);
                    return new ResponseEntity<>("Login details added successfully", HttpStatus.CREATED);
                } else
                    return new ResponseEntity<>("Something went wrong and we could not register your details. Check your password is at least 8 characters in length and contains at least one one uppercase letter, number and special character", HttpStatus.NOT_ACCEPTABLE);
            } else return new ResponseEntity<>("Something went wrong and we could not register your details. This username is already taken, please choose another and try again", HttpStatus.NOT_ACCEPTABLE);
        } else return new ResponseEntity<>("Something went wrong and we could not register your details. Please ensure your username is less than 128 characters in length and not empty, as well as only consisting of lowercase and uppercase letters and the digits 0-9.", HttpStatus.NOT_ACCEPTABLE);
    }

    @DeleteMapping("/login/delete")
    public ResponseEntity<String> deleteLogin(@RequestBody Login loginToDelete, @RequestHeader("Authorization") String authToken){
        if (authorizationService.checkValidToken(loginToDelete.getUsername(), authToken.split(" ")[1])){
            if (BCrypt.checkpw(loginToDelete.getPassword(), loginRepository.getByUsername(loginToDelete.getUsername()).getPassword())){
                loginRepository.delete(loginToDelete);
                return new ResponseEntity<>("Login data cleared for user.", HttpStatus.GONE);
            } else return new ResponseEntity<>("Password does not match. Try again.", HttpStatus.UNAUTHORIZED);
        } else return new ResponseEntity<>("Username was not found. Perhaps you forgot to register first?", HttpStatus.NOT_ACCEPTABLE);
    }

    private boolean validateUsername(String username){
        if (username.length() > 0 && username.length() <= 128) {
            return Pattern.matches("[a-zA-Z0-9]+", username);
        }
        return false;
    }

    private boolean validatePassword(String password){
        return password.length() >= 8 && Pattern.matches("(.)*(\\d)*(.)*", password) && Pattern.matches("(.)*([A-Z])*(.)*", password) && Pattern.matches("(.)*([^\\w])*(.)*", password);
    }

    private String generateNewToken(){
        StringBuilder newToken = new StringBuilder();
        Random r = new Random();
        for (int i = 0 ; i < 40; i++){
            newToken.append(validCharset.charAt(r.nextInt(validCharset.length())));
        }
        return newToken.toString();
    }
}