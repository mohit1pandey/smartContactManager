package com.scm2.SmartContactManager.Forms;

import org.springframework.web.multipart.MultipartFile;

import com.scm2.SmartContactManager.validators.ValidFile;

import io.micrometer.common.lang.NonNull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ContactForm {

    @Size(min = 3, message = "Name is Required")
    private String name;

    @Email(message = "Invalid email address") // if invalid is provided "eg: not use @gmail or @abc"
    // for regex validation
    @Pattern( 
            regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format")
    private String email;
    
    @Size(min = 10,max = 12)
    private String phoneNumber;

    @NotBlank(message = "please enter message")
    private String address;

    private String description;
    private boolean favorite;
    private String websiteLink;
    private String linkdinLink;

  

    // will create a custom annotation wchich validates the resolution and size of image
   
    @ValidFile(message = "Only PNG, JPG, and JPEG files under 2MB are allowed")
    private MultipartFile contactImage;

    private String imageUrl; //maps with pic in contact.java
    
}
