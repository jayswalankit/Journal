package com.ankit.journalApp.Service;

import com.ankit.journalApp.Repo.UserRepo;
import com.ankit.journalApp.entity.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import  static  org.mockito.Mockito.*;


public class UserDetailsServiceImplTests {
  @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @Mock
    private UserRepo userRepo;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void loadUserByUsernameTest(){
        User mockUser = new User();
        mockUser.setUserName("Aman");
        mockUser.setPassword("encodedPassword");
        mockUser.setRoles(List.of("USER"));


        when(userRepo.findByUserName("Aman"))
                .thenReturn(mockUser);

        UserDetails user = userDetailsService.loadUserByUsername("Aman");
        Assertions.assertNotNull(user);
    }
}
//8:48