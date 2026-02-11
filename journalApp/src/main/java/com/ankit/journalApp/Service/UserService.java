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

    // 🔓 Create User (Public)
    public User createUser(User user) {

        user.setPassword(
                passwordEncoder.encode(user.getPassword())
        );

        user.setRoles(List.of("USER"));

        return userRepo.save(user);
    }

    // 🔍 Find by username (Used in Auth + Journal)
    public User findByUserName(String userName) {
        return userRepo.findByUserName(userName);
    }

    // ✏️ Update logged-in user
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

    // 💾 Internal save (No password encoding)
    public void save(User user) {
        userRepo.save(user);
    }

    // ❌ Delete logged-in user
    public void deleteByUserName(String userName) {
        userRepo.deleteByUserName(userName);
    }
}
