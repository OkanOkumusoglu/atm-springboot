package com.ok.atm.dao;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.ok.atm.entities.User;

public interface UserRepository extends MongoRepository<User, Long> {

    @Override
    List<User> findAll();
}
