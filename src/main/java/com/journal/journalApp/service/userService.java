package com.journal.journalApp.service;

import com.journal.journalApp.entity.user;
import com.journal.journalApp.repository.userRapository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class userService {

    @Autowired
    private userRapository userRapository;

    private static final PasswordEncoder passwordEncoder =  new BCryptPasswordEncoder();



    public void saveNewUser(user userEntry){
        userEntry.setPassword(passwordEncoder.encode(userEntry.getPassword()));
        userEntry.setRoles(Arrays.asList("USER"));
        userRapository.save(userEntry);
    }

    public void saveUser(user userEntry){
        userRapository.save(userEntry);
    }

    public void saveAdminUser(user userEntry){
        userEntry.setPassword(passwordEncoder.encode(userEntry.getPassword()));
        userEntry.setRoles(Arrays.asList("USER, ADMIN"));
        userRapository.save(userEntry);
    }

    public List<user> getAllUsers(){
        return userRapository.findAll();
    }

    public Optional<user> findById(ObjectId id) {
        return userRapository.findById(id);
    }

    public boolean deleteById(ObjectId id) {
        userRapository.deleteById(id);
        return true;
    }
    public boolean deleteByUserName(String userName) {
        userRapository.deleteByUserName(userName);
        return true;
    }

    public user findByUserName(String userName){
        return userRapository.findByUserName(userName);
    }



}
