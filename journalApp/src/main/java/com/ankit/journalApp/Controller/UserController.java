package com.ankit.journalApp.Controller;


import com.ankit.journalApp.Service.JournalEntryService;
import com.ankit.journalApp.Service.UserService;
import com.ankit.journalApp.entity.JournalEntry;
import com.ankit.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    ///  get All users

    @GetMapping()
   public ResponseEntity<?>getAllUSers(){
        List<User>user=userService.userList();
        if(user.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(user);
    }

    ///  create User
    @PostMapping()
    public ResponseEntity<User> createUser(@RequestBody User user){
        try{
            User savedUser = userService.create(user); // DB me save karega
            return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    ///  get by id

    @GetMapping("/id/{id}")
    public ResponseEntity<User>findById(@PathVariable ObjectId id){
      Optional<User>userEntry=userService.findById(id);
      return userEntry.map(entry->new ResponseEntity<>(entry,HttpStatus.OK))
              .orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    ///  Delete by Id

    @DeleteMapping("/id/{id}")
    public ResponseEntity<User>deleteById(@PathVariable ObjectId id){
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

///  Update By Name....
@PutMapping("/name/{name}")
public ResponseEntity<User> updateByName(@PathVariable String name, @RequestBody User updateEntry) {
    try {
        User updatedUser = userService.updateByName(name, updateEntry);
        return ResponseEntity.ok(updatedUser); // 200 OK + updated user in body
    } catch (Exception e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 if user not found
    }
}
}
