package com.college.rating.UserService.service;

import com.college.rating.UserService.entity.User;

import java.util.List;

public interface UserService {

    //create
    User createUser(User user);

    List<User> getAllUsers();

    User getUser(String userId);

    User updateUser(User user, String userId);

    void deleteUser(String userId);

    List<String> getRegisteredServices();

    boolean userAlreadyExists(String userEmail);

    User getUserByEmail(String email);
}
