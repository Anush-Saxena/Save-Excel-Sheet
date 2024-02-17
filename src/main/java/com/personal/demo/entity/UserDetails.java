package com.personal.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDetails {
    @Id
    private String phoneNumber;  // Phone Number of the user
    private String name;     // Account Holder's Name
    private String userName;  // userName of the person's account
    private String password;
    private String dateOfBirth;
}
