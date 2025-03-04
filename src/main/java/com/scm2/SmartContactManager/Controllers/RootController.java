package com.scm2.SmartContactManager.Controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.scm2.SmartContactManager.Helper.Helper;
import com.scm2.SmartContactManager.Services.UserService;
import com.scm2.SmartContactManager.entities.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;

/*now methods inside of this class will execute of each and every controller */

@ControllerAdvice
public class RootController {

    private Logger logger = LoggerFactory.getLogger(RootController.class);
    @Autowired
    UserService userService;

    @ModelAttribute("loggedInUser")
    public void addLoggedInUserInformation(Model model, Authentication authentication) {

        // what if authentication is null
        if (authentication == null) {
            logger.info("authenticaion is null");

            return;
        }
        String email = Helper.getEmailOfLoggedInUser(authentication);
        // System.out.println("Username" +email)

        User user = userService.getUserByEmail(email);

        if(user==null){
            model.addAttribute("loggedInUser", null);
            return;
        }

        logger.info(user.getName());
        logger.info(user.getEmail());
        // send this ti Model
        model.addAttribute("loggedInUser", user);
        // model.addAttribute("loggedInUser", null);
    }
}
