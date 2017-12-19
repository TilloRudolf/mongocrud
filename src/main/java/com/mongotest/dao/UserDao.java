package com.mongotest.dao;

import com.mongotest.model.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserDao extends MongoRepository<UserEntity, String> {

    List<UserEntity> findAll();
    UserEntity save(UserEntity userEntity);
    UserEntity findById(String id);
    void delete(String id);
}
