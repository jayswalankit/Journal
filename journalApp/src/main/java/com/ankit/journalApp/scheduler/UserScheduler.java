package com.ankit.journalApp.scheduler;

import com.ankit.journalApp.Repo.UserRepoImpl;
import com.ankit.journalApp.Service.EmailService;
import com.ankit.journalApp.Service.SentimentalAnalysisService;
import com.ankit.journalApp.entity.JournalEntry;
import com.ankit.journalApp.entity.User;
import com.ankit.journalApp.enums.Sentiment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
         List<Sentiment> sentiments = journalEntries.stream().filter(x->x.getDate().isAfter(LocalDateTime.now().minusDays(7))).map(JournalEntry::getSentiment).toList();
          Map<Sentiment,Integer>sentimentCounts = new HashMap<>();
          for(Sentiment sentiment :  sentiments){
              if(sentiment != null){
                  sentimentCounts.put(sentiment,sentimentCounts.getOrDefault(sentiment,0)+1);
              }
              Sentiment mostFreq= null;
              int maxCount=0;
              for(Map.Entry<Sentiment, Integer> entry : sentimentCounts.entrySet()){
                  if(entry.getValue()> maxCount){
                      maxCount = entry.getValue();
                      mostFreq = entry.getKey();
                  }
              }

              if(mostFreq != null){
                  emailService.sendEmail(user.getEmail(), "Sentiment for last 7 days ", mostFreq.toString());
              }
          }

      }

  }

}
