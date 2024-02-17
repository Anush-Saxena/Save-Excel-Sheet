package com.personal.demo.service;

import com.personal.demo.dto.UpdateDetails;
import com.personal.demo.entity.UserDetails;
import com.personal.demo.entity.SignedInDetails;
import com.personal.demo.dao.CheckDao;
import com.personal.demo.dao.LoginDao;
import com.personal.demo.methods.StringHashcode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SettingsService {

    @Autowired
    private LoginDao loginDao;
    @Autowired
    private CheckDao checkDao;

    public boolean active(String requestIp){
        Optional<SignedInDetails> signedInDetails = checkDao.findById(requestIp);
        if (signedInDetails.isPresent()){
            return true;
        }
        return false;
    }

    public String updateInfo(UpdateDetails updateDetails, String requestIp){

        // Here we check if someone has signedIn or not.
        Optional<SignedInDetails> signedInDetails = checkDao.findById(requestIp);
        if (signedInDetails.isPresent()){
            UserDetails info = loginDao.findById(signedInDetails.get().getPhoneNumber()).get();

            // For uniqueness of PhoneNumber

            if (!info.getPhoneNumber().equals(updateDetails.getPhoneNumber()) && loginDao.existsById(updateDetails.getPhoneNumber())) {
                return "Not Updated! \nPhone Number already in use";
            }

            // For uniqueness of Username
            Optional<UserDetails> checkUserName = loginDao.findByUserName(updateDetails.getUserName());
            if (!updateDetails.getUserName().equals(updateDetails.getUserName()) && checkUserName.isPresent()) {
                return "Not Updated! \nUser Name already in use";
            }

            // Both Password will be checked in the hashed format

            else if (info.getPassword().equals(StringHashcode.convertToStringHash(updateDetails.getOldPassword()))) {
                info.setPassword(StringHashcode.convertToStringHash(updateDetails.getPassword()));
                info.setDateOfBirth(updateDetails.getDateOfBirth());
                info.setName(updateDetails.getName());
                info.setUserName(updateDetails.getUserName());
                SignedInDetails updateCheckDetails = signedInDetails.get();
                if (!info.getPhoneNumber().equals(updateDetails.getPhoneNumber())){
                    loginDao.deleteById(info.getPhoneNumber());
                    info.setPhoneNumber(updateDetails.getPhoneNumber());
                    updateCheckDetails.setPhoneNumber(updateDetails.getPhoneNumber());
                }
                loginDao.save(info);
                // Password is saved in the hashed form in both the places i.e. @SignedInDetails and @UserDetails

                updateCheckDetails.setPassword(StringHashcode.convertToStringHash(updateDetails.getPassword()));
                updateCheckDetails.setUserName(updateDetails.getUserName());
                checkDao.save(updateCheckDetails);
                return "Data Updated";
            } else
                return "Incorrect Password";
        }
        else
            return "You need to be Signed In! ";
    }
    public String changePassword(String oldPassword, String newPassword, String requestIp){
        Optional<SignedInDetails> signedIn = checkDao.findById(requestIp);
        if (signedIn.isPresent()){
            UserDetails info = loginDao.findById(signedIn.get().getPhoneNumber()).get();
            String hashOldPassword = StringHashcode.convertToStringHash(oldPassword);
            String hashNewPassword = StringHashcode.convertToStringHash(newPassword);
            if (info.getPassword().equals(hashOldPassword)){
                info.setPassword(hashNewPassword);
                loginDao.save(info);
                signedIn.get().setPassword(hashNewPassword);
                checkDao.save(signedIn.get());
                return "Password Changed Successfully";
            } else {
                return "Incorrect Password";
            }
        }
        else return "You need to Sign In first";
    }
    public String deleteUser(String password, String requestIp){
        Optional<SignedInDetails> signedIn = checkDao.findById(requestIp);
        if (signedIn.isPresent()){
            UserDetails info = loginDao.findById(signedIn.get().getPhoneNumber()).get();
            String hashPassword = StringHashcode.convertToStringHash(password);
            if (info.getPassword().equals(hashPassword)){
                loginDao.deleteById(info.getPhoneNumber());
                checkDao.deleteById(requestIp);
                return "User Data Deleted Successfully";
            } else {
                return "Password Incorrect";
            }
        }
        else return "You need to sign In First! ";
    }
}