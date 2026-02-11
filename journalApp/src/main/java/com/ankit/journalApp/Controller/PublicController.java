package com.ankit.journalApp.Controller;

import com.ankit.journalApp.Service.UserService;
import com.ankit.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String healthCheck() {
        return "OK";
    }

    @PostMapping("/create-user")
    public ResponseEntity<?> createUser(
            @RequestBody User user) {

        User savedUser = userService.createUser(user);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }
}
