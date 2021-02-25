package com.codeup.springblog.controller;

import com.codeup.springblog.model.User;
import com.codeup.springblog.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service("UserService")
public class UserService {
    private final UserRepository usersDao;

    public UserService (UserRepository usersDao) { this.usersDao = usersDao; }

    public User loggedInUser() {
        return usersDao.findAll().get(0);
    }


}
