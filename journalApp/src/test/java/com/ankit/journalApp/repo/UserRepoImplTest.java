package com.ankit.journalApp.repo;


import com.ankit.journalApp.Repo.UserRepoImpl;
import com.ankit.journalApp.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;


@SpringBootTest
public class UserRepoImplTest {

    @Autowired
    private UserRepoImpl userRepoImpl;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void testNewSaveUser(){
        Assertions.assertNotNull(userRepoImpl.getUserForSA());

    }
}


