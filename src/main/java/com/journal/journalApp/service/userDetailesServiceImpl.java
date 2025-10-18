package com.journal.journalApp.service;

import com.journal.journalApp.entity.user;
import com.journal.journalApp.repository.userRapository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
public class userDetailesServiceImpl implements UserDetailsService {

    @Autowired
    private userRapository userRapository;

    private static final Logger log = LoggerFactory.getLogger(userDetailesServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        user user = userRapository.findByUserName(username);
        if(user != null){
            return User.builder()
                    .username(user.getUserName())
                    .password(user.getPassword())
                    .roles(user.getRoles().toArray(new String[0]))
                    .build();
        }
        log.error("Username {} not found", username);
        throw new UsernameNotFoundException("Username not found "+username);
    }
}
