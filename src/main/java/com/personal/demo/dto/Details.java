package com.personal.demo.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Sign In Info/Details
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Details{
    private String phoneNumber = "";
    private String userName="";
    private String password="";
}