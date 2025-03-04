package com.scm2.SmartContactManager.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.scm2.SmartContactManager.Services.ContactService;
import com.scm2.SmartContactManager.entities.Contact;

@RestController   //so send data in json
@RequestMapping("/api")
public class ApiController {

    //get contact of user


    @Autowired
    private ContactService contactService;

    @GetMapping("/contact/{contactID}")
    public Contact geContact(@PathVariable String contactID){


        return contactService.getContactByID(contactID);
    }


    
}
