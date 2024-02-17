package com.personal.demo.dto;

import com.personal.demo.entity.UserDetails;

public class UpdateDetails extends UserDetails {
    private String oldPassword;
    public void setOldPassword(String oldPassword){
        this.oldPassword = oldPassword;
    }
    public String getOldPassword() {
        return oldPassword;
    }
}
