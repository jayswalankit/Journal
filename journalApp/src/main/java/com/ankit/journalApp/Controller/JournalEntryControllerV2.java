package com.ankit.journalApp.Controller;


import com.ankit.journalApp.Service.JournalEntryService;
import com.ankit.journalApp.entity.JournalEntry;
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

    ///  to create  entries.
    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry myEntry) {
        try{
            myEntry.setDate(LocalDateTime.now());
            journalService.create(myEntry);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    ///  To get list of entries...
    @GetMapping("/getAll")
    public ResponseEntity<?> getAll() {
        List<JournalEntry>all=journalService.journalEntryList();
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
    @DeleteMapping("/id/{myId}")
    public ResponseEntity<?> deleteById(@PathVariable ObjectId myId) {
        journalService.deleteById(myId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @PutMapping("/id/{myId}")
    public ResponseEntity<JournalEntry> update(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry) {
        JournalEntry updated = journalService.update(myId, newEntry);
        if (updated != null) {
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
