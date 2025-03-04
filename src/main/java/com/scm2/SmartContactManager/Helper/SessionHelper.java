package com.scm2.SmartContactManager.Helper;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.HttpSession;

@Component
public class SessionHelper {

    public static void removeMessage() {
        // Get the current request attributes
        ServletRequestAttributes attributes = 
            (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        
        if (attributes != null) {
            // Retrieve the current HttpSession
            HttpSession session = attributes.getRequest().getSession(false);
            
            if (session != null) {
                // Remove the message attribute from the session
                session.removeAttribute("message");
            }
        }
    }
}

/* check each and every class */