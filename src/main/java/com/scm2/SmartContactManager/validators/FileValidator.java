package com.scm2.SmartContactManager.validators;

import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class FileValidator implements ConstraintValidator<ValidFile, MultipartFile> {

    private long maxSize = 1024 * 1024 * 2; // Default 2MB
    private String[] allowedTypes = {"image/png", "image/jpeg", "image/jpg"};

    @Override
    public void initialize(ValidFile constraintAnnotation) {
        this.maxSize = constraintAnnotation.maxSize();
        this.allowedTypes = constraintAnnotation.allowedTypes();
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null || file.isEmpty()) {
            // to let empty image to be update set true
            
            return true;

        }

        // ✅ Check file size
        if (file.getSize() > maxSize) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("File should be lesser than " + (maxSize / (1024 * 1024)) + " MB")
                   .addConstraintViolation();
            return false;
        }

        // ✅ Check file type
        String fileType = file.getContentType();
        if (fileType == null || !Arrays.asList(allowedTypes).contains(fileType)) { //check wether the file exite in the list 
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Only PNG, JPG, and JPEG files are allowed")
                   .addConstraintViolation();
            return false;
        }

        return true;
    }
}
