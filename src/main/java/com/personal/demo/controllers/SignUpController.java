package com.personal.demo.controllers;

import com.personal.demo.entity.UserDetails;
import com.personal.demo.service.SignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignUpController {
    @Autowired
    private SignUpService signUpService;

    @RequestMapping(value = "/signUp", method = RequestMethod.POST)
    public ResponseEntity<?> addUser(@RequestBody UserDetails userDetails) {
        Object response = signUpService.addUser(userDetails);
        if (response.getClass().equals("".getClass())){
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Created Successfully", HttpStatus.CREATED);
    }
}
