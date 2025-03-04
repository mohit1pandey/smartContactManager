package com.scm2.SmartContactManager.Services;

import java.util.List;
import java.util.Optional;

import com.scm2.SmartContactManager.entities.User;

public interface UserService {
// declare the methods for USER crud here and inplement them on UserServiceImpl

    User saveUser(User user);
     
    Optional <User> getUserById(String id);

    Optional <User> updateUser(User user);

    void deleteUser(String id);

    boolean isUserExist(String id);

    boolean isUserExistByEmail(String id);

    List<User> getAllUsers();

    User  getUserByEmail(String email);
    
}
