package com.scm2.SmartContactManager.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;

import com.scm2.SmartContactManager.entities.Contact;
import com.scm2.SmartContactManager.entities.User;

public interface ContactService {
    
    Contact saveContact(Contact contact);

    Contact updateContact(Contact contact);

    List <Contact> getAllContacts();

    // Optional<Contact> getContactByID(String id);


    Contact getContactByID(String id);

    void deleteContact(String id);


 
    Page<Contact> searchByName(String name,int page,int size , String sortBy, String direction,User user);

    Page<Contact> serahByEmail(String email,int page,int size , String sortBy, String direction,User user);

    Page<Contact> serachByNumber(String phoneNumer,int page,int size , String sortBy, String direction,User user);

    // most imp

    List<Contact> getContactsByUser(String userId);

    Page<Contact>  getByUser(User user,int page, int size,String sortBy,String direction) ;

}
