package com.scm2.SmartContactManager.Config;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.apache.catalina.authenticator.jaspic.PersistentProviderRegistrations.Providers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.scm2.SmartContactManager.Helper.AppConstants;
import com.scm2.SmartContactManager.Reposiotries.UserRepo;
import com.scm2.SmartContactManager.entities.Providors;
import com.scm2.SmartContactManager.entities.User;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component

public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    Logger logger = LoggerFactory.getLogger(CustomAuthenticationSuccessHandler.class);

    @Autowired
    private UserRepo userRepo;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        logger.info("CustomAuthenticationSuccessHandler");

        // identify the providoer
        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;

        String clientId = token.getAuthorizedClientRegistrationId();
        System.out.println(clientId + "this is client id \n"); // shoud show id like google or facebook

        OAuth2User oauthUser = (OAuth2User) token.getPrincipal(); // the rest of the object

        // ierate over the userx
        oauthUser.getAttributes().forEach((key, value) -> {
            logger.info("{}=>{}", key, value);
        });

        // now create user
        User user = new User();

        user.setUserID(UUID.randomUUID().toString());
        user.setRoleList(List.of(AppConstants.ROLE_USER));
        user.setEmailVarified(true);
        user.setEnabled(true);
        user.setPassword("dummy");

        if (clientId.equalsIgnoreCase("google")) {

            user.setEmail(oauthUser.getAttribute("email").toString());
            user.setProfilePic(oauthUser.getAttribute("picture").toString());
            user.setName(oauthUser.getAttribute("name").toString());
            user.setProviderUserID(oauthUser.getName());
            user.setProvidor(Providors.GOOGLE);
            user.setAbout("This account is created using google.");

        } else if (clientId.equalsIgnoreCase("github")) {

            String email = oauthUser.getAttribute("email") != null ? oauthUser.getAttribute("email").toString()
                    : oauthUser.getAttribute("login").toString() + "@gmail.com";
            String picture = oauthUser.getAttribute("avatar_url");
            String name = oauthUser.getAttribute("login").toString();
            String providerUserId = oauthUser.getName();

            user.setEmail(email);
            user.setProfilePic(picture);
            user.setName(name);
            user.setProviderUserID(providerUserId);
            user.setProvidor(Providors.GITHUB);

            user.setAbout("This account is created using github");

        }

        else if (clientId.equalsIgnoreCase("facebook")) {

        }

        else {
            logger.info("unkonwn provioder");
        }

        // github

        // facebook

        // /*before redirect get data from authentiation and save data to DB */
        // DefaultOAuth2User user=(DefaultOAuth2User) authentication.getPrincipal();
        // /*
        // user.getAttributes().forEach((key,value)->{
        // logger.info("{}:{}",key,value);
        // });

        // logger.info(user.getAuthorities().toString());
        // */

        // User user1= new User();
        // user1.setName(user.getAttribute("name").toString());
        // user1.setEmail(user.getAttribute("email").toString());
        // user1.setProfilePic(user.getAttribute("picture").toString());
        // user1.setPassword("randomPassword");
        // user1.setUserID(UUID.randomUUID().toString());
        // user1.setEnabled(true);
        // user1.setEmailVarified(true);
        // user1.setProviderUserID(user.getName());
        // user1.setRoleList(List.of(AppConstants.ROLE_USER));
        // user1.setAbout("this is provided by google");


        User user2
        =userRepo.findByEmail(user.getEmail()).orElse(null); //emil is set to user

        if(user2==null){
        userRepo.save(user);
        System.out.println("user saved");
        }



        

         new DefaultRedirectStrategy().sendRedirect(request, response, "/user/profile"); // sping security specific
        
        // response.sendRedirect("/user/profile"); // to redirect it is java EE behviour 
    }

}
