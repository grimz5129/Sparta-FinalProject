package com.sparta.projectapi.services;

import com.sparta.projectapi.repositories.LoginRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    @Autowired
    LoginRepository loginRepository;

    public boolean checkValidToken(String username, String authToken){
        if (loginRepository.existsByUsername(username)){
            return loginRepository.getByUsername(username).getCurrentToken().equals(authToken);
        }
        return false;
    }
}
