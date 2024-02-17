package com.personal.demo.service;

import com.personal.demo.entity.UserDetails;
import com.personal.demo.dao.LoginDao;
import com.personal.demo.methods.StringHashcode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SignUpService {

    @Autowired
    private LoginDao loginDao;
    public Object addUser(UserDetails requestUserDetails){
        /*
            Here uniqueness of Phone Number is checked.
            Only 1 account is kept with 1 Phone Number since it is also a Primary key in DataBase.
        */
        Optional<UserDetails> signUpDetails = loginDao.findById(requestUserDetails.getPhoneNumber());
        if (signUpDetails.isEmpty()){
            /*
                Here we check the uniqueness of the username.
                So they can Login with their username also.
            */
            signUpDetails = loginDao.findByUserName(requestUserDetails.getUserName());
            if (signUpDetails.isEmpty()) {
                /*
                    Here password is converted into hash form and then saved to MySql Table.
                    We haven't checked if the dto are filled or not since it is the part of frontend.
                */
                requestUserDetails.setPassword(StringHashcode.convertToStringHash(requestUserDetails.getPassword()));
                loginDao.save(requestUserDetails);
                return true;
            }
            else{
                return "User Name pre Occupied!";
            }
        }
        else
            return "Phone Number already Exists! ";
    }
}
