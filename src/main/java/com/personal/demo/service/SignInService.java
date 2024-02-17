package com.personal.demo.service;

import com.personal.demo.entity.SignedInDetails;
import com.personal.demo.entity.UserDetails;
import com.personal.demo.dao.CheckDao;
import com.personal.demo.dao.LoginDao;
import com.personal.demo.methods.StringHashcode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class SignInService {
    @Autowired
    private LoginDao loginDao;
    @Autowired
    private CheckDao checkDao;

    /*
        This part is for the users using phoneNumber to LogIn in their account.
    */
    public Object verifyUser(String numberOrName, String password, String clientIp){
        if (!checkDao.existsById(clientIp)) {
            Optional<UserDetails> optionalDetails;
            if (numberOrName.matches("\\d+")) {
                System.out.println("inside If");
                optionalDetails = loginDao.findById(numberOrName);
            } else {
                System.out.println("inside Else");
                optionalDetails = loginDao.findByUserName(numberOrName);
            }
            if (optionalDetails.isPresent()) {
                System.out.println("okokokokok");
                // Here info will contain the password in hashed form.

                UserDetails info = optionalDetails.get();
                String hashPassword = StringHashcode.convertToStringHash(password);
                if (info.getPassword().equals(hashPassword)) {

                /*
                Since Info contains the password in hashed form, so it is stored in the same form in signInDetails.
                */
                    String clientToken = "user:"+ UUID.randomUUID();
                    checkDao.save(new SignedInDetails(clientToken, info.getUserName(), info.getPhoneNumber(), info.getPassword()));
                    return true;
                } else
                    return "Wrong Password";
            } else
                return "User Doesn't exists";
        }
        else return "You need to SignOut First ";
    }
    public String signOutUser(String requestIp){
        Optional<SignedInDetails> signedIn = checkDao.findById(requestIp);
        if (signedIn.isPresent()){
            checkDao.deleteById(requestIp);
            return "Signed Out";
        }
        else return "No account signedIn";
    }
}
