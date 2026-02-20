package com.ankit.journalApp.Service;

import com.ankit.journalApp.Repo.UserRepo;
import com.ankit.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    private final PasswordEncoder passwordEncoder =
            new BCryptPasswordEncoder();

 // for creating user
    public User createUser(User user) {

        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        user.setRoles(List.of("USER"));

        return userRepo.save(user);
    }

    /// Get all users...
     public List<User>userList(){
         return userRepo.findAll();
     }

    //  Find by username
    public User findByUserName(String userName) {
        return userRepo.findByUserName(userName);
    }

  ///  for updating username and password nt entries...
    public void updateUser(String userName, User updateEntry) {

        User existingUser = userRepo.findByUserName(userName);

        if (existingUser == null) {
            throw new RuntimeException("User not found");
        }

        if (updateEntry.getUserName() != null) {
            existingUser.setUserName(updateEntry.getUserName());
        }

        if (updateEntry.getPassword() != null) {
            existingUser.setPassword(
                    passwordEncoder.encode(updateEntry.getPassword())
            );
        }

        userRepo.save(existingUser);
    }

  ///  user ke ander ke cheje like enteries jaisi chejo ko save karne ke liye hoti hai not for password and username
    public void save(User user) {
        userRepo.save(user);
    }


    public void deleteByUserName(String userName) {
        userRepo.deleteByUserName(userName);
    }

    public User saveAdmin(User user) {
        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        user.setRoles(List.of("USER","ADMIN"));

        return  userRepo.save(user);
    }
}
