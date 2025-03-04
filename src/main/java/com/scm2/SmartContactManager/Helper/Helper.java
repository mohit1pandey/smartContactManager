package com.scm2.SmartContactManager.Helper;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;



@Component
public class Helper {

    public static String getEmailOfLoggedInUser(Authentication authentication) {

        // if user is form any auth2
        if (authentication instanceof OAuth2AuthenticationToken) {

            OAuth2AuthenticationToken oAuth2Token = (OAuth2AuthenticationToken) authentication;

            String clientID = oAuth2Token.getAuthorizedClientRegistrationId();

            OAuth2User oauthUser = (OAuth2User) oAuth2Token.getPrincipal();// get user or principal

            String username = "";

            if (clientID.equalsIgnoreCase("google")) {

                System.out.println("getting email from google");

                username = oauthUser.getAttribute("email");
            }

            else if (clientID.equalsIgnoreCase("github")) {

                System.out.println("getting email form github");

                username = oauthUser.getAttribute("email") != null ? oauthUser.getAttribute("email").toString()
                        : oauthUser.getAttribute("login").toString() + "@gmail.com";
            } else {

                System.out.println("unkonwn provider");

            }
            return username;

        }

        else {

            System.out.println("getting email form loacal DB");

            return authentication.getName(); // or principal.getName(); both methods are same ek jaise nhi ek hi hain

        }
    }

}

/*
 * Here authentication represents the auth token and in case of simple DB auth
 * it we can direclty get name but in caseof
 * OAuth we need to disect the token
 */