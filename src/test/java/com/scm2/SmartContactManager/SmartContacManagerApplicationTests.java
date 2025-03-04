package com.scm2.SmartContactManager;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.scm2.SmartContactManager.Services.EmailService;

@SpringBootTest
class SmartContactManagerApplicationTests {

@Autowired
private EmailService emailService;

	// @Disabled
	@Test
	void contextLoads() {
		System.out.println("ran");
	}


	// @Disabled
	@Test
	void sendEmailTest(){
		try {
			emailService.sendEmail("mohitpandey22505@gmail.com", "Test Email", "Hello, test mail!");
			System.out.println("Email sent successfully!");
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}
}
