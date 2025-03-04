package com.scm2.SmartContactManager.validators;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented                                      // to document for javadoc
@Target({ElementType.FIELD})                     // the scope of annotation 
@Retention(RetentionPolicy.RUNTIME)              // When to retain memory   
@Constraint(validatedBy = {FileValidator.class}) // the helper class name extending an validation interface

public @interface ValidFile {



    String message() default "Invalid file"; // Default error message
    
    Class<?>[] groups() default {}; // Used for grouping constraints
    
    Class<? extends Payload>[] payload() default {}; // Can be used for custom error handling

    long maxSize() default 2 * 1024 * 1024; // Default max file size: 5MB
    
    String[] allowedTypes() default {"image/jpeg", "image/png"}; // Allowed MIME types
}