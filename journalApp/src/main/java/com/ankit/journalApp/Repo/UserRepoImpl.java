package com.ankit.journalApp.Repo;

import com.ankit.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepoImpl {


    @Autowired
    private MongoTemplate mongoTemplate;

    public List<User> getUserForSA() {

        System.out.println("DB Name: " + mongoTemplate.getDb().getName());
        System.out.println("Collection: " + mongoTemplate.getCollectionName(User.class));

        Query query = new Query();
        query.addCriteria(new Criteria().andOperator(
                Criteria.where("email").regex("^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$"),
                Criteria.where("sentimentAnalysis").is(true)
        ));
        List<User> users = mongoTemplate.find(query, User.class);
        System.out.println("All users: " + users.size());
        System.out.println("Users size: " + users.size());
        List<String> emails = users.stream()
                .map(User::getEmail)
                .toList();

        System.out.println("Emails: " + emails);
        return users;
    }
}
