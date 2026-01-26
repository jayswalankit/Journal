package com.ankit.journalApp.Service;

import com.ankit.journalApp.Repo.JournalEntryRepo;
import com.ankit.journalApp.Repo.UserRepo;
import com.ankit.journalApp.entity.JournalEntry;
import com.ankit.journalApp.entity.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepo userRepo;

    ///  To create an Entry

    public User create(User user){
      return   userRepo.save(user);
    }

    ///  To get all journal enteries


    public List<User>userList(){
         return userRepo.findAll();
    }

    /// find by id...
    public Optional<User> findById(ObjectId id){
        return userRepo.findById(id)
                .stream().findFirst();
    }

    ///  Delete By id

    public void deleteById(ObjectId id){
     userRepo.deleteById(id);
    }

///  Update by name
public User updateByName(String userName, User updateEntry) {
    User existingUser = userRepo.findByUserName(userName);

    if (existingUser == null) {
        throw new RuntimeException("User not found with name: " + userName);
    }


    if (updateEntry.getUserName() != null) {
        existingUser.setUserName(updateEntry.getUserName());
    }
    if (updateEntry.getPassword() != null) {
        existingUser.setPassword(updateEntry.getPassword());
    }

    // Agar journalEntries bhi update karna hai
    if (updateEntry.getJournalEntries() != null && !updateEntry.getJournalEntries().isEmpty()) {
        existingUser.setJournalEntries(updateEntry.getJournalEntries());
    }

    return userRepo.save(existingUser);
}


    public User findByUserName(String userName) {
    return userRepo.findByUserName(userName);
    }

    public void update(User user) {
    userRepo.save(user);
    }
}
