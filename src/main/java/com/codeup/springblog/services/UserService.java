package com.codeup.springblog.services;

import com.codeup.springblog.model.Provider;
import com.codeup.springblog.model.User;
import com.codeup.springblog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("UserService")
public class UserService {

    @Autowired
    private UserRepository repo;

    public User loggedInUser() {
        System.out.println(SecurityContextHolder.getContext().getAuthentication());
        Object test = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


    public void processOAuthPostLogin(String username, String email, String password, String img) {
        User existUser = repo.findByEmail(email);

        if (existUser == null) {
            User newUser = new User();
            newUser.setUsername(username + "-" + UUID.randomUUID().toString().substring(0, 5));
            newUser.setEmail(email);
            newUser.setPassword(password);
            newUser.setProvider(Provider.GOOGLE);
            newUser.setEnabled(true);
            newUser.setProfileImagePath(img);

            repo.save(newUser);
        }

    }

}