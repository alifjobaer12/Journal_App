package com.journal.journalApp.service;

import com.journal.journalApp.entity.journalEntry;
import com.journal.journalApp.entity.user;
import com.journal.journalApp.repository.journalRapository;
import com.journal.journalApp.repository.userRapository;
import org.bson.types.ObjectId;
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

    @Transactional
    public void  saveEntry(journalEntry journalEntry, String userName){
        try {
            user user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            journalEntry save = JournalRapository.save(journalEntry);
            user.getJournalEntries().add(save);
            userService.saveEntry(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
            user.getJournalEntries().removeIf(journalEntry -> journalEntry.getJournalId().equals(id));
            JournalRapository.deleteById(id);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
