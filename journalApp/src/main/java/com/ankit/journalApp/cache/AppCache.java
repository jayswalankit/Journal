package com.ankit.journalApp.cache;


import com.ankit.journalApp.Repo.ConfigJournalAppRepo;
import com.ankit.journalApp.entity.ConfigJournalAppEntity;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AppCache {

    public enum keys{
        WEATHER_API;
    }


    @Autowired
    ConfigJournalAppRepo configJournalAppRepo;
    public  Map<String,String> appCache ;

    @PostConstruct
    public void init(){
        appCache =new HashMap<>();
        List<ConfigJournalAppEntity>all=configJournalAppRepo.findAll();
        for(ConfigJournalAppEntity configJournalAppEntity : all){
            appCache.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
        }

    }
}
