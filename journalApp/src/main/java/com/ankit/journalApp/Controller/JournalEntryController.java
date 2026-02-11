package com.ankit.journalApp.Controller;

import com.ankit.journalApp.Service.JournalEntryService;
import com.ankit.journalApp.Service.UserService;
import com.ankit.journalApp.entity.JournalEntry;
import com.ankit.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalService;

    @Autowired
    private UserService userService;

    // 🔐 GET ALL
    @GetMapping
    public ResponseEntity<?> getAllJournals() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String userName = authentication.getName();

        User user = userService.findByUserName(userName);

        return new ResponseEntity<>(
                user.getJournalEntries(),
                HttpStatus.OK
        );
    }

    // 🔐 GET BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getJournalById(
            @PathVariable ObjectId id) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String userName = authentication.getName();

        Optional<JournalEntry> journal =
                journalService.findById(id);

        return journal
                .map(entry ->
                        new ResponseEntity<>(entry, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 🔐 CREATE
    @PostMapping
    public ResponseEntity<?> createJournal(
            @RequestBody JournalEntry entry) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String userName = authentication.getName();

        JournalEntry saved =
                journalService.create(entry, userName);

        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // 🔐 UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<?> updateJournal(
            @PathVariable ObjectId id,
            @RequestBody JournalEntry entry) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String userName = authentication.getName();

        JournalEntry updated =
                journalService.update(id, entry, userName);

        if (updated == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(updated, HttpStatus.OK);
    }

    // 🔐 DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteJournal(
            @PathVariable ObjectId id) {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        String userName = authentication.getName();

        journalService.deleteById(id, userName);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
