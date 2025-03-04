package com.scm2.SmartContactManager.Services.Impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.scm2.SmartContactManager.Helper.AppConstants;
import com.scm2.SmartContactManager.Helper.ResousceNotFoundException;
import com.scm2.SmartContactManager.Reposiotries.UserRepo;
import com.scm2.SmartContactManager.Services.UserService;
import com.scm2.SmartContactManager.entities.User;



@Service
public class UserServiceeImpl implements UserService{


    @Autowired
    private UserRepo userRepo;

    private Logger logger= LoggerFactory.getLogger(this.getClass());


    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User saveUser(User user) {
       
        // generate user id 
        String id = UUID.randomUUID().toString();
        user.setUserID(id);
        
        // password encode
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //set the user ROle

        user.setRoleList(List.of(AppConstants.ROLE_USER));
        return userRepo.save(user);
    }

    @Override
    public Optional<User> getUserById(String id) {
        return userRepo.findById(id);
    }

    @Override
    public Optional<User> updateUser(User user) {
        User user2= userRepo.findById(user.getUserID()).orElseThrow(()-> new ResousceNotFoundException());
        
        user2.setName(user.getName());
        user2.setEmail(user.getEmail());
        user2.setPassword(user.getPassword());
        user2.setAbout(user.getAbout());
        user2.setPhoneNumber(user.getPhoneNumber());
        user2.setProfilePic(user.getProfilePic());
        user2.setEnabled(user.isEnabled());
        user2.setEmailVarified(user.isEmailVarified());
        user2.setProvidor(user.getProvidor());
        user2.setProviderUserID((user.getProviderUserID()));

        //now save this user to db
        User save= userRepo.save(user2);

        return Optional.ofNullable(save); //will return optionl of save or optionl of null


    }

    @Override
    public void deleteUser(String id) {

        User user2= userRepo.findById(id).orElseThrow(()-> new ResousceNotFoundException());
        userRepo.delete(user2);

    }

    @Override
    public boolean isUserExist(String id) {
        User user2= userRepo.findById(id).orElse(null);

        return user2!=null ? true:false;

    }


    @Override
    public boolean isUserExistByEmail(String email) {
       User user=userRepo.findByEmail(email).orElse(null);
       return user != null?true:false;

    }

    @Override
    public List<User> getAllUsers() {
       return userRepo.findAll();
    }

    @Override
    public User getUserByEmail(String email) {
    
    //   return userRepo.findByEmail(email).orElseThrow(()->new ResousceNotFoundException("User Not Found")); 
    return userRepo.findByEmail(email).orElse(null);
      //orlese thow only works in case of optional
    }


}
