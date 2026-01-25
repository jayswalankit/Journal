package com.ankit.journalApp.Service;

import com.ankit.journalApp.Repo.JournalEntryRepo;
import com.ankit.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class JournalEntryService {

    @Autowired
    JournalEntryRepo journalRepo;

    ///  To create an Entry

    public JournalEntry create(@RequestBody JournalEntry journalEntryCreate){
      return   journalRepo.save(journalEntryCreate);
    }

    ///  To get all journal enteries

    @GetMapping
    public List<JournalEntry>journalEntryList(){
         return journalRepo.findAll();
    }

    /// find by id...
    public Optional<JournalEntry> findById(ObjectId id){
        return journalRepo.findById(id)
                .stream().findFirst();
    }

    ///  Delete By id

    public void deleteById(ObjectId id){
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
