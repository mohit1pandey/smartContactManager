package com.scm2.SmartContactManager.Controllers;

import java.util.List;
import java.util.UUID;

import javax.print.DocFlavor.STRING;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.LoggerFactoryFriend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.scm2.SmartContactManager.Forms.ContactForm;
import com.scm2.SmartContactManager.Forms.ContactSearchForm;
import com.scm2.SmartContactManager.Helper.AppConstants;
import com.scm2.SmartContactManager.Helper.Helper;
import com.scm2.SmartContactManager.Helper.Message;
import com.scm2.SmartContactManager.Helper.MessageType;
import com.scm2.SmartContactManager.Reposiotries.UserRepo;
import com.scm2.SmartContactManager.Services.ContactService;
import com.scm2.SmartContactManager.Services.ImageService;
import com.scm2.SmartContactManager.Services.UserService;
import com.scm2.SmartContactManager.entities.Contact;
import com.scm2.SmartContactManager.entities.User;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.var;

@Controller
@RequestMapping("/user/contacts")
public class ContactController {

    @Autowired
    private UserService userService;
    @Autowired
    private ContactService contactService;
    @Autowired
    private ImageService imageService;

    private Logger logger = LoggerFactory.getLogger(ContactController.class);

    // add contact page

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String addContactView(Model model) {

        // set default value

        ContactForm contactForm = new ContactForm();
        contactForm.setName("Mohit Pandey");

        contactForm.setFavorite(true); // set check box default value
        model.addAttribute("contactForm", contactForm);

        return "user/add_contact";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String saveContact(@Valid @ModelAttribute ContactForm contactForm, BindingResult rBindingResult,
            Authentication authentication, HttpSession session) {

        // validate the form

        // image process

        logger.info("file info: {}", contactForm.getContactImage().getOriginalFilename()); // image file name

        if (rBindingResult.hasErrors()) {

            // if form data has some error so set session attribute
            logger.info("Validation errors found!");
            rBindingResult.getFieldErrors().forEach(error -> {
                logger.info("Field: {}, Error: {}", error.getField(), error.getDefaultMessage());
            });

            session.setAttribute("message", Message.builder()
                    .content("Please correct the following errors")
                    .type(MessageType.red)
                    .build());

            return "user/add_contact"; // return and show same page with error messages and if error so it will be sent
                                       // to contact for

        }

        // to get use

        String email = Helper.getEmailOfLoggedInUser(authentication);
        User user = userService.getUserByEmail(email);

        Contact contact = new Contact();

        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setCont_address(contactForm.getAddress());
        contact.setCont_discription(contactForm.getDescription());
        contact.setFav(contactForm.isFavorite());
        contact.setCont_websiteLink(contactForm.getWebsiteLink());
        contact.setCont_leinkedInLink(contactForm.getLinkdinLink());
        contact.setUser(user);

        // if file is not null or not empty

        if (contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {

            // file upload code and create a public id file name
            String filename = UUID.randomUUID().toString();

            String fileUrl = imageService.uplaodImage(contactForm.getContactImage(), filename);

            contact.setCont_pricture(fileUrl);
            contact.setCloudPublicId(filename);

            // set uplic id in DB

        }

        // save this contact
        contactService.saveContact(contact);

        

        System.out.println(contactForm);
        // process the form data.

        // set success meassage
        session.setAttribute("message", Message.builder()
                .content("Contact Saved secessfully")
                .type(MessageType.green)
                .build());

        return "redirect:/user/contacts/add";
    }

    // view contacts
    @GetMapping
    public String viewContacts(

            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy, // this should be in the entity
            @RequestParam(value = "direction", defaultValue = "asc") String direction,

            Model model, Authentication authentication) {

        String username = Helper.getEmailOfLoggedInUser(authentication);

        User user = userService.getUserByEmail(username);

        Page<Contact> pageContacts = contactService.getByUser(user, page, size, sortBy, direction);

        model.addAttribute("pageContacts", pageContacts);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        model.addAttribute("contactSearchForm", new ContactSearchForm());

        return "/user/contacts";

    }

    @GetMapping("/search")
    public String searchHadler(
            // @RequestParam("field") String field, // this we will get form from
            // @RequestParam("keyword") String keyword,
            @ModelAttribute ContactSearchForm contactSearchForm,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sortBy", defaultValue = "name") String sortBy, // this should be in the entity
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            Model model,
            Authentication authentication)

    {
        // extract data here

        String field = contactSearchForm.getField();
        String keyword = contactSearchForm.getKeyword();

        logger.info("field {} value {}", field, keyword);

        String username = Helper.getEmailOfLoggedInUser(authentication);

        User user = userService.getUserByEmail(username);

        Page<Contact> pageContacts = null;

        if (field.equalsIgnoreCase("name")) {
            pageContacts = contactService.searchByName(keyword, page, size, sortBy, direction, user);
        } else if (field.equalsIgnoreCase("email")) {
            pageContacts = contactService.serahByEmail(keyword, page, size, sortBy, direction, user);
        } else {
            pageContacts = contactService.serachByNumber(keyword, page, size, sortBy, direction, user);
        }

        model.addAttribute("pageContacts", pageContacts);
        model.addAttribute("pageSize", AppConstants.PAGE_SIZE);
        model.addAttribute("contactSearchForm", contactSearchForm);

        logger.info("contactSearchForm {}", contactSearchForm);

        return "user/search";
    }

    @GetMapping("/delete/{id}")
    public String deleteContact(@PathVariable("id") String contactId) {

        contactService.deleteContact(contactId);

        logger.info("contact deleted {}", contactId);
        return "redirect:/user/contacts";
    }

    // update contact form view this will be called on click on edit button

    @GetMapping("/view/{id}")
    public String updateContactFormView(@PathVariable("id") String contactId, Model model) {

        var contact = contactService.getContactByID(contactId);

        ContactForm contactForm = new ContactForm();

        contactForm.setName(contact.getName());
        contactForm.setEmail(contact.getEmail());
        contactForm.setPhoneNumber(contact.getPhoneNumber());
        contactForm.setAddress(contact.getCont_address());
        contactForm.setDescription(contact.getCont_discription());
        contactForm.setFavorite(contact.isFav());
        contactForm.setLinkdinLink(contact.getCont_leinkedInLink());
        contactForm.setWebsiteLink(contact.getCont_websiteLink());

        contactForm.setImageUrl(contact.getCont_pricture());

        model.addAttribute("contactForm", contactForm);

        model.addAttribute("contactId", contactId);

        return "user/update_contact_view";
    }

    @PostMapping("/update/{contactId}")
    public String updateContact(@PathVariable("contactId") String contactId,
            @Valid @ModelAttribute ContactForm contactForm, BindingResult rBindingResult,
            Model model, HttpSession session) {

        logger.info("contact update controller");

        if (rBindingResult.hasErrors()) {

            rBindingResult.getFieldErrors().forEach(error -> {
                logger.info("Field: {}, Error: {}", error.getField(), error.getDefaultMessage());
            });

            return "redirect:/user/contacts/view/" + contactId;
        }

        // Contact contact = new Contact(); beacause we want all other values to be
        // default
        Contact contact = contactService.getContactByID(contactId);

        // this will ensure that the not filled fields remain as previous.eeeeeeeee

        contact.setId(contactId); // will get form path variable
        contact.setName(contactForm.getName());
        contact.setEmail(contactForm.getEmail());
        contact.setPhoneNumber(contactForm.getPhoneNumber());
        contact.setCont_address(contactForm.getAddress());
        contact.setCont_discription(contactForm.getDescription());
        contact.setFav(contactForm.isFavorite());
        contact.setCont_websiteLink(contactForm.getWebsiteLink());
        contact.setCont_leinkedInLink(contactForm.getLinkdinLink());

        logger.info("file info: {}", contactForm.getContactImage().getOriginalFilename()); // image file name

        if (contactForm.getContactImage() != null && !contactForm.getContactImage().isEmpty()) {
            // process image if chaged

            String fileName = UUID.randomUUID().toString();

            String imageUrl = imageService.uplaodImage(contactForm.getContactImage(), fileName); // getcontimage is
                                                                                                 // multipart

            logger.info(imageUrl);

            contact.setCont_pricture(imageUrl);

            contact.setCloudPublicId(fileName);

            contactForm.setImageUrl(imageUrl); // to show this new URL

        }

        // to do set thsi imge url and pulic id (file name);

        // update the contact
        var updatedCont = contactService.updateContact(contact);

        model.addAttribute(contactId, contact);

        session.setAttribute("message", Message.builder()
                .content("contact updated successfully")
                .type(MessageType.green)
                .build());

        return "redirect:/user/contacts/view/" + contactId;
    }
}
