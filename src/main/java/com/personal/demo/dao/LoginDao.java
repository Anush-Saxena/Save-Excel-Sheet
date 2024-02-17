package com.personal.demo.dao;

import com.personal.demo.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginDao extends JpaRepository<UserDetails, String> {

    boolean existsByUserName(String userName);

    Optional<UserDetails> findByUserName(String userName);
}
