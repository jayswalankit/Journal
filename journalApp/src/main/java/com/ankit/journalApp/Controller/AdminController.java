package com.ankit.journalApp.Controller;

import com.ankit.journalApp.Service.UserService;
import com.ankit.journalApp.cache.AppCache;
import com.ankit.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {


    private final UserService userService;

    public AdminController (UserService userService){
        this.userService=userService;
    }

    @Autowired
    private AppCache appCache;


    @GetMapping("/getAllUsers")
    public ResponseEntity<List<User>> getAllUsers(){
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
   @GetMapping("clear-app-cache")
   public void clearAppCache(){
 appCache.init();
   }
}
