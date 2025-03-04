package com.scm2.SmartContactManager.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.scm2.SmartContactManager.Forms.UserForm;
import com.scm2.SmartContactManager.Helper.Message;
import com.scm2.SmartContactManager.Helper.MessageType;
import com.scm2.SmartContactManager.Services.Impl.UserServiceeImpl;

import com.scm2.SmartContactManager.entities.User;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Controller
public class PageController {

    @Autowired
    private UserServiceeImpl userServiceeImpl;

    // models aare use full to send data to html template dyanamic from coltrollers.

    @RequestMapping("/home")

    public String home(Model model) {
        System.out.println("Home page controller");
        model.addAttribute("name", "Substring Technologies");
        model.addAttribute("GitRepo", "http://github.com/abc/scm");
        model.addAttribute("Youtube", "Learn Code");
        return "home";
    }

    @RequestMapping("/about")
    public String about() {

        System.out.println("About page loading");
        return "about";
    }

    @RequestMapping("/contact")
    public String contact() {

        System.out.println("contact page loading");
        return "contact";
    }

    @RequestMapping("/services")
    public String services() {

        System.out.println("serives page loading");
        return "services";
    }

    @GetMapping("/register")
    public String register(Model model) {

        UserForm userForm = new UserForm(); // send this data to form as well

        userForm.setName("mohit");
        userForm.setAbout("this is about me");
        model.addAttribute("userForm", userForm);

        System.out.println("register page loading");
        return "register";
    }

    @GetMapping("/login")
    public String login() {

        System.out.println("login page loading");
        return "login";

    }

    // processing regitster

    @RequestMapping(value = "/do-register", method = RequestMethod.POST) // @PostMapping("/do-register")

    public String processRegister(@Valid @ModelAttribute UserForm userForm, BindingResult rBindingResult,
            HttpSession session) {

        // BindingResult must cum after UserForm

        /*
         * here in this function we have to
         * i) fetch the data
         * ii) validate the data
         * iii) save to database
         * iv) redirect to login page
         */

        // @valid is for user form to validate and @BindingResult

        // Validate the data
        if (rBindingResult.hasErrors()) {
            // if there are error it will conteain them and we can show them in
            // register.html

            // Log errors
            System.out.println("Validation errors:");
            rBindingResult.getFieldErrors().forEach(error -> {
                System.out.println("Field: " + error.getField() + ", Error: " + error.getDefaultMessage());
            });

            return "register";
        }

        /*
         * 
         * User user=User.builder()
         * .name(userForm.getName())
         * .email(userForm.getEmail())
         * .about(userForm.getAbout())
         * .phoneNumber(userForm.getPhoneNumber())
         * .password(userForm.getPassword())
         * .profilePic(
         * "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.istockphoto.com%2Fphotos%2Fdefault-profile-image&psig=AOvVaw04r5gykOE8PMxaAw4Xr0JA&ust=1737544095784000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCPDku6fWhosDFQAAAAAdAAAAABAE")
         * .build();
         * 
         */ // this builder function is not leeting to insert default values in the DB:

        User user = new User();

        // Set values from UserForm
        user.setName(userForm.getName());
        user.setEmail(userForm.getEmail());
        user.setPassword(userForm.getPassword());
        user.setAbout(userForm.getAbout());
        user.setPhoneNumber(userForm.getPhoneNumber());
        user.setProfilePic("https://example.com/default-profile-pic.jpg"); // Default profile picture

        User savedUser = userServiceeImpl.saveUser(user); // registration seccessfull print it

        // now create a session

        Message message = Message.builder().content("Registration Successful").type(MessageType.yellow).build();

        System.out.println(message.toString());
        System.out.println(message.getType().toString());
        session.setAttribute("message", message);
        System.out.println(message.getContent());

        return "redirect:/register";
    }



}
