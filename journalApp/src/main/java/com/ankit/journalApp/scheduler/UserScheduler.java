package com.ankit.journalApp.scheduler;

import com.ankit.journalApp.Repo.UserRepoImpl;
import com.ankit.journalApp.Service.EmailService;
import com.ankit.journalApp.Service.SentimentalAnalysisService;
import com.ankit.journalApp.entity.JournalEntry;
import com.ankit.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserScheduler {
    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepoImpl userRepoImpl;

    @Autowired
    private  SentimentalAnalysisService sentimentalAnalysisService;


    @Scheduled(cron = "0 * * ? * *")
  public void fetchUsersAndSendMail(){
      List<User>users= userRepoImpl.getUserForSA();
      for(User user:users){
          List<JournalEntry>journalEntries = user.getJournalEntries();
         List<String> fileredEntries = journalEntries.stream().filter(x->x.getDate().isAfter(LocalDateTime.now().minusDays(7))).map(JournalEntry::getContent).toList();
      String entry = String .join("",fileredEntries);
       String sentiment =   sentimentalAnalysisService.getSentiments(entry);
       emailService.sendEmail(user.getEmail(), "Sentiments for last 7 days" ,sentiment);


      }

  }

}
