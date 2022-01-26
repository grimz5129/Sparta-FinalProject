package com.sparta.projectapi.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ListController {

    @PostMapping("/list/create")
    public ResponseEntity<String> createNewList(){
        return new ResponseEntity<>("Dummy Message", HttpStatus.I_AM_A_TEAPOT);
    }
    
    @GetMapping("/list/view")
    public ResponseEntity<String> viewList(){
        return new ResponseEntity<>("Dummy Message", HttpStatus.I_AM_A_TEAPOT);
    }
    
    @DeleteMapping("/list/delete")
    public ResponseEntity<String> deleteList(){
        return new ResponseEntity<>("Dummy Message", HttpStatus.I_AM_A_TEAPOT);
    }

}
