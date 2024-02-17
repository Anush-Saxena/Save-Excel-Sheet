package com.personal.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SignedInDetails {
    @Id
    String clientToken;
    String userName;
    String phoneNumber;
    String password;
}
