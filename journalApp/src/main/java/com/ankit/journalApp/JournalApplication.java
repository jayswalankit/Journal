package com.ankit.journalApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class JournalApplication {

	public static void main(String[] args) {
	ConfigurableApplicationContext context = SpringApplication.run(JournalApplication.class, args);

        String mongoUri = context.getEnvironment().getProperty("spring.data.mongodb.uri");
        System.out.println("--------------------------------------------------");
        System.out.println("🔥 MY MONGO URI IS: " + mongoUri);
        System.out.println("--------------------------------------------------");
	}
    ///  Platform Transactional Manager hi transaction ko manage karta hai wo isse rollback and commit  karta hai it is implemented by MongoTransactional manager
    @Bean
    public PlatformTransactionManager add(MongoDatabaseFactory dbFactory){
        return new MongoTransactionManager(dbFactory);
    }
    /// MongoDatabaseFactory is used to connect with database .....
}
