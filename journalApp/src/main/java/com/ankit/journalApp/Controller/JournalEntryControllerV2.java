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
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalEntryControllerV2 {

    @Autowired
    JournalEntryService journalService;
    @Autowired
    UserService userService;

    ///  to create  entries.
    @PostMapping("{userName}")
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry,@PathVariable String userName) {
        try{
            journalService.create(myEntry,userName);
            return new ResponseEntity<>(myEntry,HttpStatus.CREATED);
        } catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    ///  To get list of entries...
    @GetMapping("{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName) {
       User user= userService.findByUserName(userName);
        List<JournalEntry>all=user.getJournalEntries();
       if(all!=null&&!all.isEmpty()){
           return new ResponseEntity<>(all,HttpStatus.OK);
       }
       return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    ///  get by id
    @GetMapping("/id/{myid}")
    public ResponseEntity<JournalEntry> findById(@PathVariable ObjectId myid) {
        Optional<JournalEntry>journalEntry=journalService.findById(myid);
               return journalEntry.map(entry->new ResponseEntity<>(entry,HttpStatus.OK))
                .orElseGet(()->new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    /// delete  by id
    @DeleteMapping("/id/{userName}/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId,@PathVariable String userName) {
        journalService.deleteById(myId,userName);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PutMapping("/id/{userName}/{myId}")
    public ResponseEntity<JournalEntry> update(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry,@PathVariable String userName) {
        JournalEntry updated = journalService.update(myId, newEntry);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
