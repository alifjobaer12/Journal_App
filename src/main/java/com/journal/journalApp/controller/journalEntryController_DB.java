package com.journal.journalApp.controller;

import com.journal.journalApp.entity.journalEntry;
import com.journal.journalApp.service.journalEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class journalEntryController_DB {

    @Autowired
    private journalEntryService Service;

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<journalEntry> all = Service.getAll();
        if(all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody journalEntry journal) {
        try {
            journal.setDate(LocalDateTime.now());
            Service.saveEntry(journal);
            return new ResponseEntity<>(journal, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{fid}")
    public ResponseEntity<?> findJournal(@PathVariable String fid) {
        Optional <journalEntry> findId = Service.findById(fid);
        if(findId.isPresent()) {
            return new ResponseEntity<>(findId.get(),HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{fid}")
    public ResponseEntity<?> deleteJournal(@PathVariable String fid) {

        Optional <journalEntry> findId = Service.findById(fid);
        if(findId.isPresent()) {
            Service.deleteById(fid);
            return new ResponseEntity<>(findId.get(),HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/id/{fid}")
    public ResponseEntity<?> updateJournal(@PathVariable String fid, @RequestBody journalEntry newEntry) {

        journalEntry oldEntry = Service.findById(fid).orElse(null);

        if(newEntry == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else if(oldEntry == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            oldEntry.setJournalDescription(newEntry.getJournalDescription()!=null && !newEntry.getJournalDescription().equals("") ? newEntry.getJournalDescription() : oldEntry.getJournalDescription());
            oldEntry.setJournalName(newEntry.getJournalName()!=null && !newEntry.getJournalName().equals("") ? newEntry.getJournalName() : oldEntry.getJournalName());
        }
        Service.saveEntry(oldEntry);
        return new ResponseEntity<>(oldEntry, HttpStatus.OK);
    }
}
