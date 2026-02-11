package com.ankit.journalApp.Service;

import com.ankit.journalApp.Repo.JournalEntryRepo;
import com.ankit.journalApp.entity.JournalEntry;
import com.ankit.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepo journalRepo;

    @Autowired
    private UserService userService;

    // ✅ CREATE
    @Transactional
    public JournalEntry create(JournalEntry journalEntry, String userName) {

        User user = userService.findByUserName(userName);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        journalEntry.setDate(LocalDateTime.now());

        JournalEntry saved = journalRepo.save(journalEntry);

        user.getJournalEntries().add(saved);

        userService.save(user); // No password encoding

        return saved;
    }

    // ✅ FIND BY ID
    public Optional<JournalEntry> findById(ObjectId id) {
        return journalRepo.findById(id);
    }

    // ✅ UPDATE (Ownership safe)
    @Transactional
    public JournalEntry update(ObjectId id,
                               JournalEntry newEntry,
                               String userName) {

        User user = userService.findByUserName(userName);

        boolean exists = user.getJournalEntries()
                .stream()
                .anyMatch(j -> j.getId().equals(id));

        if (!exists) return null;

        Optional<JournalEntry> optional = journalRepo.findById(id);

        if (optional.isEmpty()) return null;

        JournalEntry entry = optional.get();

        if (newEntry.getTitle() != null)
            entry.setTitle(newEntry.getTitle());

        if (newEntry.getContent() != null)
            entry.setContent(newEntry.getContent());

        entry.setDate(LocalDateTime.now());

        return journalRepo.save(entry);
    }

    // ✅ DELETE (Ownership safe)
    @Transactional
    public void deleteById(ObjectId id, String userName) {

        User user = userService.findByUserName(userName);

        user.getJournalEntries()
                .removeIf(j -> j.getId().equals(id));

        userService.save(user);

        journalRepo.deleteById(id);
    }
}
