package com.journal.journalApp.service;

import com.journal.journalApp.entity.journalEntry;
import com.journal.journalApp.repository.journalRapository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class journalEntryService {

    @Autowired
    private journalRapository JournalRapository;

    public void  saveEntry(journalEntry Entry){
        JournalRapository.save(Entry);
    }

    public List<journalEntry> getAll(){
        return JournalRapository.findAll();
    }

    public Optional<journalEntry> findById(String id) {
        return JournalRapository.findById(id);
    }

    public boolean deleteById(String id) {
        JournalRapository.deleteById(id);
        return true;
    }

}
