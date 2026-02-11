package com.ankit.journalApp.Controller;

import com.ankit.journalApp.Service.UserService;
import com.ankit.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    // 🔐 Get Logged-in User
    @GetMapping
    public ResponseEntity<?> getUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String userName = authentication.getName();

        User user = userService.findByUserName(userName);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    // 🔐 Update Logged-in User
    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String userName = authentication.getName();

        userService.updateUser(userName, user);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 🔐 Delete Logged-in User
    @DeleteMapping
    public ResponseEntity<?> deleteUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String userName = authentication.getName();

        userService.deleteByUserName(userName);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
