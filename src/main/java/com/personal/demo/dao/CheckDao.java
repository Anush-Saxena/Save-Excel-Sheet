package com.personal.demo.dao;

import com.personal.demo.entity.SignedInDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckDao extends JpaRepository<SignedInDetails, String> {
}
