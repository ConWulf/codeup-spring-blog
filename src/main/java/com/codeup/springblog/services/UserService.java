package com.codeup.springblog.services;

import com.codeup.springblog.model.CustomOAuth2User;
import com.codeup.springblog.model.Provider;
import com.codeup.springblog.model.User;
import com.codeup.springblog.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("UserService")
public class UserService extends DefaultOAuth2UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User loggedInUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user =  super.loadUser(userRequest);
        return new CustomOAuth2User(user);
    }

    @Autowired
    private UserRepository repo;

    public void processOAuthPostLogin(String username, String email) {
        User existUser = repo.findByUsername(username);

        if (existUser == null) {
            User newUser = new User();
            newUser.setUsername(username);
            newUser.setEmail(email);
            newUser.setPassword(passwordEncoder.encode(UUID.randomUUID().toString()));
            newUser.setProvider(Provider.GOOGLE);
            newUser.setEnabled(true);

            repo.save(newUser);
        }

    }

}