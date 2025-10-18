package com.journal.journalApp.service;

import com.journal.journalApp.entity.journalEntry;
import com.journal.journalApp.entity.user;
import com.journal.journalApp.repository.journalRapository;
import com.journal.journalApp.repository.userRapository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Component
public class journalEntryService {

    @Autowired
    private journalRapository JournalRapository;
    @Autowired
    private  userService userService;
    @Autowired
    private userRapository userRapository;

    private static final Logger log =  LoggerFactory.getLogger(journalEntryService.class);

    @Transactional
    public void  saveEntry(journalEntry journalEntry, String userName){
        try {
            user user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            journalEntry save = JournalRapository.save(journalEntry);
            user.getJournalEntries().add(save);
            userService.saveUser(user);
            log.info("Journal New Entry Saved Successfully");
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
    public void  saveEntry(journalEntry journalEntry){
        try {
            JournalRapository.save(journalEntry);
            log.info("Journal Entry Update Successfully");
        } catch (Exception e) {
//            System.out.println(e.getMessage());
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<journalEntry> getAll(){
        return JournalRapository.findAll();
    }

    public Optional<journalEntry> findById(ObjectId id) {
        return JournalRapository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId id, String userName) {
        try {
            user user = userService.findByUserName(userName);
            boolean isRemoved = user.getJournalEntries().removeIf(journalEntry -> journalEntry.getJournalId().equals(id));
            if(isRemoved) {
                JournalRapository.deleteById(id);
                userService.saveUser(user);
                log.info("Journal Entry Delete Successfully");
                return true;
            }
            else {
                log.warn("Journal Entry with id {} not found", id);
                throw new RuntimeException("Journal Entry with id " + id + " not found");
            }
        } catch (Exception e) {
//            System.out.println(e.getMessage());
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
