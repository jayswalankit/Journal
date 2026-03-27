package com.ankit.journalApp;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableTransactionManagement
@EnableScheduling
public class JournalApplication {

	public static void main(String[] args) {
	ConfigurableApplicationContext context = SpringApplication.run(JournalApplication.class, args);

        String mongoUri = context.getEnvironment().getProperty("spring.data.mongodb.uri");
        System.out.println("--------------------------------------------------");
        System.out.println("🔥 MY MONGO URI IS: " + mongoUri);
        System.out.println("--------------------------------------------------");
        ConfigurableEnvironment environment=context.getEnvironment();
        System.out.println(environment.getActiveProfiles()[0]);
	}
    ///  Platform Transactional Manager hi transaction ko manage karta hai wo isse rollback and commit  karta hai it is implemented by MongoTransactional manager
    @Bean
    public PlatformTransactionManager add(MongoDatabaseFactory dbFactory){
        return new MongoTransactionManager(dbFactory);
    }
    /// MongoDatabaseFactory is used to connect with database .....

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
