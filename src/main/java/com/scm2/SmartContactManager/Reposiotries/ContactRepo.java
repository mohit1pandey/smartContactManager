package com.scm2.SmartContactManager.Reposiotries;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

// import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.scm2.SmartContactManager.entities.Contact;
import com.scm2.SmartContactManager.entities.User;

@Repository
public interface ContactRepo extends JpaRepository<Contact,String>{ //<class, @id type>

    // if extea method required so write here 

    public Page<Contact> findByUser(User user,Pageable pageable); //iski free me impelmentation mil jayegi 

 
    @Query("SELECT c FROM Contact c WHERE c.user.id=:userId")  //JSQL or we can use mysql as well
    public List<Contact> findByUserId(String userId);


 

    @Query("SELECT c FROM Contact c WHERE c.user = :user AND c.name LIKE %:name%")
    Page<Contact> findByNameAndUser(@Param("name")String name,@Param("user")User user, Pageable pageable);  //select * form table where name like "%keyword%";
    
    // or use jpa method strucutre
    
    Page<Contact> findByUserAndEmailContaining(User user, String email, Pageable pageable);

    Page<Contact> findByUserAndPhoneNumberContaining(User user, String phoneNumber, Pageable pageable);

} 