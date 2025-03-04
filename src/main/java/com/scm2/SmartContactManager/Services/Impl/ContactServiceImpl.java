package com.scm2.SmartContactManager.Services.Impl;

import com.scm2.SmartContactManager.Helper.ResousceNotFoundException;

import java.lang.foreign.Linker.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.scm2.SmartContactManager.Reposiotries.ContactRepo;
import com.scm2.SmartContactManager.Services.ContactService;
import com.scm2.SmartContactManager.entities.Contact;
import com.scm2.SmartContactManager.entities.User;

import lombok.var;

@Service

public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepo contactRepo;

    @Override
    public Contact saveContact(Contact contact) {
        String contactId= UUID.randomUUID().toString();
        contact.setId(contactId);
        return contactRepo.save(contact);
    }

    @Override
    public Contact updateContact(Contact contact) {
      
        Contact contactFromDB= contactRepo.findById(contact.getId()).orElseThrow(()-> new ResousceNotFoundException());

        contactFromDB.setName(contact.getName());
        contactFromDB.setEmail(contact.getEmail());
        contactFromDB.setPhoneNumber(contact.getPhoneNumber());
        contactFromDB.setCont_address(contact.getCont_address());
        contactFromDB.setCont_discription(contact.getCont_discription());
        contactFromDB.setCont_leinkedInLink(contact.getCont_leinkedInLink());
        contactFromDB.setCont_websiteLink(contact.getCont_websiteLink());
        contactFromDB.setCont_pricture(contact.getCont_pricture());
        contactFromDB.setFav(contact.isFav());

        contactFromDB.setCloudPublicId(contact.getCloudPublicId());

        return contactRepo.save(contactFromDB);
    }

    @Override
    public List<Contact> getAllContacts() {
     
        return contactRepo.findAll();
    }

    @Override
    public Contact getContactByID(String id) {
     return contactRepo.findById(id).orElseThrow(()->new ResousceNotFoundException("contact not found with the id"));
    // return contactRepo.findById(id); in case of optional 
    }

    @Override
    public void deleteContact(String id) {
      
       var contact= contactRepo.findById(id).orElseThrow(()->new ResousceNotFoundException("contact not found with the id"));

        // contactRepo.deleteById(contact);
        contactRepo.delete(contact);
    }



    @Override
    public List<Contact> getContactsByUser(String userId) {
       return contactRepo.findByUserId(userId);

    }

    @Override
    // sortBy is proeprty like name or phon no eamil 
    public Page<Contact> getByUser(User user,int page, int size,String sortBy,String direction) {
        

        Sort sort= direction.equals("desc")? Sort.by(sortBy).descending():Sort.by(sortBy).ascending();
      

        var pageable= PageRequest.of(page, size, sort);

      return  contactRepo.findByUser(user,pageable);
      
    }

    @Override
    public Page<Contact> searchByName(String name, int page, int size, String sortBy, String direction,User user) {
        
        
        Sort sort= direction.equals("desc")? Sort.by(sortBy).descending(): Sort.by(sortBy).ascending();

        var pageable= PageRequest.of(page, size, sort);

        return contactRepo.findByNameAndUser(name, user,pageable);


    }

    @Override
    public Page<Contact> serahByEmail(String email, int page, int size, String sortBy, String direction,User user) {
        
        Sort sort= direction.equals("desc")? Sort.by(sortBy).descending(): Sort.by(sortBy).ascending();
        var pageable= PageRequest.of(page, size, sort);
        return contactRepo.findByUserAndEmailContaining(user,email,pageable);


    }

    @Override
    public Page<Contact> serachByNumber(String phoneNumber, int page, int size, String sortBy, String direction, User user) {
        
        Sort sort= direction.equals("desc")? Sort.by(sortBy).descending(): Sort.by(sortBy).ascending();
        var pageable= PageRequest.of(page, size, sort);
        return contactRepo.findByUserAndPhoneNumberContaining(user,phoneNumber,pageable);
    }
    

}
