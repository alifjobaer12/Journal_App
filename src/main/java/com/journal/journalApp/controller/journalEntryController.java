package com.journal.journalApp.controller;

import com.journal.journalApp.entity.journalEntry;
import com.journal.journalApp.entity.user;
import com.journal.journalApp.service.journalEntryService;
import com.journal.journalApp.service.userService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class journalEntryController {

    @Autowired
    private journalEntryService Service;
    @Autowired
    private userService userService;

    @GetMapping("/{userName}")
    public ResponseEntity<?> getAllEntryOfUser(@PathVariable String userName) {
        user user = userService.findByUserName(userName);
        List<journalEntry> all = user.getJournalEntries();
        if(all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/{userName}")
    public ResponseEntity<?> createEntry(@RequestBody journalEntry journal, @PathVariable String userName) {
        try {
            Service.saveEntry(journal, userName);
            return new ResponseEntity<>(journal, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{fid}")
    public ResponseEntity<?> findJournal(@PathVariable ObjectId fid) {
        Optional <journalEntry> findId = Service.findById(fid);
        if(findId.isPresent()) {
            return new ResponseEntity<>(findId.get(),HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{userName}/id/{fid}")
    public ResponseEntity<?> deleteJournal(@PathVariable ObjectId fid, @PathVariable String userName) {

        Optional <journalEntry> findId = Service.findById(fid);
        if(findId.isPresent()) {
            Service.deleteById(fid, userName);
            return new ResponseEntity<>(findId.get(),HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{userName}/id/{fid}")
    public ResponseEntity<?> updateJournal(
            @PathVariable ObjectId fid,
            @RequestBody journalEntry newEntry,
            @PathVariable String userName) {

        journalEntry oldEntry = Service.findById(fid).orElse(null);

        if(newEntry == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else if(oldEntry == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else {
            oldEntry.setJournalName(newEntry.getJournalName()!=null && !newEntry.getJournalName().equals("") ? newEntry.getJournalName() : oldEntry.getJournalName());
            oldEntry.setJournalDescription(newEntry.getJournalDescription()!=null && !newEntry.getJournalDescription().equals("") ? newEntry.getJournalDescription() : oldEntry.getJournalDescription());
        }
        Service.saveEntry(oldEntry);
        return new ResponseEntity<>(oldEntry, HttpStatus.OK);
    }
}
