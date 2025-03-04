// this will controll the all routes realated to user actions.

package com.scm2.SmartContactManager.Controllers;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.scm2.SmartContactManager.Helper.Helper;
import com.scm2.SmartContactManager.Services.UserService;
import com.scm2.SmartContactManager.entities.User;

@Controller
@RequestMapping("/user")

public class UserController {

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(UserController.class);

    private SecurityContextHolder sContextHolder = new SecurityContextHolder();

    /*
     * 
     * // make a function to send logged in user info to in each request to do so
     * use @ModelAttribute
     * 
     * @ModelAttribute
     * public void addLoggedInUserInformation(Model model, Authentication
     * authentication) {
     * 
     * String email = Helper.getEmailOfLoggedInUser(authentication);
     * 
     * // System.out.println("Username" +email);
     * 
     * User user = userService.getUserByEmail(email);
     * 
     * logger.info(user.getName());
     * logger.info(user.getEmail());
     * 
     * // send this ti Modle
     * 
     * model.addAttribute("loggedinUser", user);
     * }
     */
    // user dashboard page

    @RequestMapping(value = "/dashboard")

    public String userDashboard() {

        return "user/dashboard";
    }

    // user profile

    @RequestMapping(value = "/profile")

    public String userProfile() {

        return "user/profile";
    }

    // user add contacts page

    // user view contacts

    // user edit contact

    // user search contact

}
