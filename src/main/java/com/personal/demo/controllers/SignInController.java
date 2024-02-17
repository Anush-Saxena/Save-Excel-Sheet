package com.personal.demo.controllers;

import com.personal.demo.dto.Details;
import com.personal.demo.service.SignInService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
public class SignInController {
    @Autowired
    private SignInService signInService;

    @PostMapping(value = "/signIn")
    public ResponseEntity<?> verifyUser(HttpServletRequest request, @RequestBody Details details){
        String numberOrName;
        if (!details.getPhoneNumber().isEmpty()){
            numberOrName = details.getPhoneNumber();
        }
        else{
            numberOrName = details.getUserName();
        }
        String password = details.getPassword();
        Object response =signInService.verifyUser(numberOrName, password, request.getRemoteAddr());
        if (response.getClass().equals("".getClass())){
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("SignedIn Successfully", HttpStatus.OK);
    }

    @DeleteMapping(value = "/signOut")
    public String signOutUser(HttpServletRequest request){
        String requestIp = request.getRemoteAddr();
        return signInService.signOutUser(requestIp);
    }
}
