package com.journal.journalApp.service;

import com.journal.journalApp.entity.user;
import com.journal.journalApp.repository.userRapository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class userService {

    @Autowired
    private userRapository userRapository;

    public void  saveEntry(user userEntry){
        userRapository.save(userEntry);
    }

    public List<user> getAll(){
        return userRapository.findAll();
    }

    public Optional<user> findById(ObjectId id) {
        return userRapository.findById(id);
    }

    public boolean deleteById(ObjectId id) {
        userRapository.deleteById(id);
        return true;
    }

    public user findByUserName(String userName){
        return userRapository.findByUserName(userName);
    }

}
