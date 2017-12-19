package com.mongotest.service;

import com.mongotest.model.User;

import java.util.List;

public interface UserService {

    List<User> getUsers();
    User getUser(String id);
    String update(User user);
    String remove(String id);
}
