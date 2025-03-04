package com.scm2.SmartContactManager.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name="contact")

public class Contact {

    @Id
    private String id;
    @Column(name = "cont_name")
    private String name;
    @Column(name="cont_email")
    private String email;
    @Column(name="cont_phoneNumber")
    private String phoneNumber;
    private String cont_address;
    @Column(length = 1000)
    private String cont_pricture;
    private String cont_discription;
    private boolean isFav=false;
    // private List<String> socialLinks=new ArrayList<>();
    private String cont_websiteLink;
    private String cont_leinkedInLink;


    private String cloudPublicId;
    //one entry will contain only one user in BD one row per user.

    @ManyToOne
    @JsonIgnore
    private User user;

        /* user may have multiple links */

    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch = FetchType.EAGER,orphanRemoval = true)
    private List<SocialLiks> socialLiks=new ArrayList<>();
}
