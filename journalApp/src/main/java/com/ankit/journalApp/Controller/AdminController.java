package com.ankit.journalApp.Controller;

import com.ankit.journalApp.Service.UserService;
import com.ankit.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private  UserService userService;

    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getAllUsers(){
       List<User> all= userService.userList();
       if(all!=null&&!all.isEmpty()){
           return new ResponseEntity<>(all, HttpStatus.FOUND);
       }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
   @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody User user){
     User Admin =   userService.saveAdmin(user);
         if(Admin!=null){
             return new ResponseEntity<>(Admin,HttpStatus.CREATED);

         }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);

   }

}
