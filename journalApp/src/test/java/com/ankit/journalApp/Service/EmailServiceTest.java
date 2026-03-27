package com.ankit.journalApp.Service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTest {



    @Autowired
    private EmailService emailService;


    @Test
    void testSendMail(){
        String email = System.getenv("MAIL_USERNAME");
        System.out.println("USERNAME: " + System.getenv("MAIL_USERNAME"));
        System.out.println("PASSWORD: " + System.getenv("MAIL_PASSWORD"));
        emailService.sendEmail(email,"Testing Java Mail Sender","site, works successfully");
    }
}
