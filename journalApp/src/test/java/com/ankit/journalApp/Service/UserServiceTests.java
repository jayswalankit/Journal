package com.ankit.journalApp.Service;

import com.ankit.journalApp.Repo.UserRepo;
import com.ankit.journalApp.entity.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {

    @Autowired
    private UserRepo userRepo;


    @Test
    @Disabled
    public void testadd(){
      assertEquals(4,2+2);
    }

    @Test
    @Disabled
    public void findByUserName(){
        assertNotNull(userRepo.findByUserName("Vipul"));
    }

    @Test
    @Disabled
    public void deleteByUserName(){
        User user= new User();
        user.setUserName("Ankesh");
        userRepo.save(user);
        System.out.println("saved"+userRepo.findByUserName("Ankesh"));
        userRepo.deleteByUserName("Ankesh");
        System.out.println("Delete "+userRepo.findByUserName("Ankesh"));
        assertTrue(userRepo.findByUserName("Ankesh")==null);
    }

    @Test
    @Disabled
    public void testUserEntries(){
        User user=userRepo.findByUserName("Azad123");
        assertTrue(!user.getJournalEntries().isEmpty());
    }

    @ParameterizedTest
    @CsvSource({
            "1,1,2",
            "2,10,12",
            "3,3,6"
    })
    @Disabled
    public void test(int a,int b,int expected){
        assertEquals(expected,a+b);
    }

    @ParameterizedTest
    @ValueSource(strings= {
            "Ankit",
            "Azad123"
    })
    public void testFindByUserName(String name){
        assertNotNull(userRepo.findByUserName(name),"passed for: "+name);
    }

    @BeforeEach
    ///  haar test case  se pehele agar kuch initilized karni hai to ye method
    void setUp(){

    }

    @BeforeAll
    ///  haar test case se pehle ye chalega
    static void setUpOne(){

    }
    // similar for after
}
//.\mvnw.cmd clean install se work kar jata hai One drive me hone ke baad bhi