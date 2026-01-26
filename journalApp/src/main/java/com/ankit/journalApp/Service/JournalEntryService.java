package com.ankit.journalApp.Service;

import com.ankit.journalApp.Repo.JournalEntryRepo;
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
public class JournalEntryService {

    @Autowired
    JournalEntryRepo journalRepo;

    @Autowired
    UserService userService;

    ///  To create an Entry

    public void create( JournalEntry journalEntryCreate,String userName){
        User user=userService.findByUserName(userName);
        journalEntryCreate.setDate(LocalDateTime.now());
        JournalEntry save = journalRepo.save(journalEntryCreate);
        user.getJournalEntries().add(save);
        userService.create(user);
    }

    public void create( JournalEntry journalEntryCreate){
        journalRepo.save(journalEntryCreate);
    }

    ///  To get all journal enteries


    public List<JournalEntry>journalEntryList(){
         return journalRepo.findAll();
    }

    /// find by id...
    public Optional<JournalEntry> findById(ObjectId id){
        return journalRepo.findById(id)
                .stream().findFirst();
    }

    ///  Delete By id

    public void deleteById(ObjectId id, String userName){
        User user=userService.findByUserName(userName);
        user.getJournalEntries().removeIf(x->x.getId().equals(id));
        userService.update(user);
     journalRepo.deleteById(id);
    }

    ///  Update by Id
    public  JournalEntry update( ObjectId id,JournalEntry updateEntry){

        return  journalRepo.findById(id)
              .stream()
              .map(extingEntry->{extingEntry.setTitle(updateEntry.getTitle());
               extingEntry.setContent(updateEntry.getContent());
               extingEntry.setDate(LocalDateTime.now());
              return  journalRepo.save(extingEntry);})
                .findFirst().orElse(null);
    }


}
