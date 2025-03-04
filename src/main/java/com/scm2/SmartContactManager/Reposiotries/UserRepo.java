package com.scm2.SmartContactManager.Reposiotries;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.scm2.SmartContactManager.entities.User;



public interface UserRepo extends JpaRepository<User,String>{ //<class, @id type>

    // if extea method required so write here 

    Optional<User> findByEmail(String email);  //jap will make this
    
} 