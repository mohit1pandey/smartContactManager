package com.scm2.SmartContactManager.Forms;

import org.springframework.boot.SpringBootConfiguration;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank; //this validation package
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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString // why

public class UserForm {

  @Size(min = 3, message = "Min Three char are required")
  private String name;

  
  @NotBlank(message = "Email is required")  // if nothing is provided
  @Email(message = "Invalid email address") //if invalid is provided "eg: not use @gmail or @abc"
  @Pattern(                                 //for regex validation
    regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",     
    message = "Invalid email format"
  )
  private String email;
  @Size(min = 6, message = "Min 6 chars are required")
  private String password;
  @NotBlank(message = "About is required")
  private String about;
  @Size(min = 10, max = 12, message = "Invalid phone number")
  private String phoneNumber;

}
