package com.codeup.springblog.services;

import com.codeup.springblog.model.CustomOAuth2User;
import com.codeup.springblog.model.User;
import com.codeup.springblog.model.UserWithRoles;
import com.codeup.springblog.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsLoader extends DefaultOAuth2UserService implements UserDetailsService {
    private final UserRepository userDao;

    public UserDetailsLoader(UserRepository userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String signIn) throws UsernameNotFoundException {
        User user;
        if(signIn.contains("@")) {
            user = userDao.findByEmail(signIn);
        } else {
            user = userDao.findByUsername(signIn);
        }
        if (user == null) {
            throw new UsernameNotFoundException("no user found for username: "+signIn);
        }
        return new UserWithRoles(user);
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User user =  super.loadUser(userRequest);
        return new CustomOAuth2User(user);
    }
}
