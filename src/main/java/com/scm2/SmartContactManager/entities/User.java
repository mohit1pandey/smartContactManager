package com.scm2.SmartContactManager.entities;


import java.beans.SimpleBeanInfo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity // to makr as entity
@Table(name="user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder // IF USINF BUILDER METHOD SO IT WONT INITILISE DEFAULE VALUE USE DEFAULT CONSTRUCTOR INSTEAD

public class User implements UserDetails {

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY) 
    //if user id is string so no auto increment.
    private String userID;

    @Column(name="user_name",nullable = false)
    private String name;
 
    @Column(unique = true, nullable = false)
    private String email;
    
    @Getter(value = AccessLevel.NONE) 
    private String password;

    @Column(length = 1000)
    private String about;

    private String profilePic;

    private String phoneNumber;
    // info

    @Getter(value = AccessLevel.NONE) //wont let lambok to generate getter and setters.
    private boolean enabled=true;  //enum in same package  //diabable if form lamboak to defile custo
    private boolean emailVarified=false;
    private boolean phoneVarified=false;

    // sign up mothed //self, google ,gihub 

    @Enumerated(value = EnumType.STRING)
    private Providors providor =Providors.SELF;
    private String providerUserID;


    /* here we start mapping  */

    @OneToMany (mappedBy="user",cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)//one user has multiple contacts
    private List<Contact> contacts= new ArrayList<>();



    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roleList= new ArrayList<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
      
       return roleList.stream()
                   .map(SimpleGrantedAuthority::new)
                   .collect(Collectors.toList());
            //return collection of role list
    }


    @Override
    public String getUsername() {
        
        return this.email; //here email be username.
    }


    @Override
    public boolean isEnabled(){

        return this.enabled;
    }

    @Override
    public String getPassword(){
        return this.password;
    }

}
