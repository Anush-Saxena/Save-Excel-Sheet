package com.personal.demo.dao;

import com.personal.demo.entity.Mapper;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataDao extends JpaRepository<Mapper,Integer> {

    Optional<Mapper> findByUserName(String userName);
}
