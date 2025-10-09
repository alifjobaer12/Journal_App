package com.journal.journalApp.controller;

import com.journal.journalApp.entity.journalEntry;
import com.journal.journalApp.entity.user;
import com.journal.journalApp.service.journalEntryService;
import com.journal.journalApp.service.userService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class journalEntryController {

    @Autowired
    private journalEntryService journalService;
    @Autowired
    private userService userService;

    @GetMapping                                                 //  All Journal of a user
    public ResponseEntity<?> getAllEntryOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        user user = userService.findByUserName(userName);
        List<journalEntry> all = user.getJournalEntries();
        if(all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping                                                //  Create a Journal
    public ResponseEntity<?> createEntry(@RequestBody journalEntry journal) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            journalService.saveEntry(journal, userName);
            return new ResponseEntity<>(journal, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/id/{fid}")                                    //  Find Journal By Id
    public ResponseEntity<?> findJournal(@PathVariable ObjectId fid) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        user User = userService.findByUserName(userName);
        List<journalEntry> idCollect = User.getJournalEntries().stream().filter(journalEntryId -> journalEntryId.getJournalId().equals(fid)).collect(Collectors.toList());
        if(idCollect != null && !idCollect.isEmpty()) {
            Optional <journalEntry> findId = journalService.findById(fid);
            return new ResponseEntity<>(findId.get(),HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{fid}")                      //  Delete Journal By Id
    public ResponseEntity<?> deleteJournal(@PathVariable ObjectId fid) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        Optional <journalEntry> findId = journalService.findById(fid);
        if(findId.isPresent()) {
            journalService.deleteById(fid, userName);
            return new ResponseEntity<>(findId.get(),HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/id/{fid}")                         //  Update Journal By Id
    public ResponseEntity<?> updateJournal(
            @PathVariable ObjectId fid,
            @RequestBody journalEntry newEntry) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        user User = userService.findByUserName(userName);

        List<journalEntry> idCollect = User.getJournalEntries().stream().filter(journalEntryId -> journalEntryId.getJournalId().equals(fid)).collect(Collectors.toList());
        if(!idCollect.isEmpty()) {
            journalEntry oldEntry = journalService.findById(fid).orElse(null);
            oldEntry.setJournalName(newEntry.getJournalName()!=null && !newEntry.getJournalName().equals("") ? newEntry.getJournalName() : oldEntry.getJournalName());
            oldEntry.setJournalDescription(newEntry.getJournalDescription()!=null && !newEntry.getJournalDescription().equals("") ? newEntry.getJournalDescription() : oldEntry.getJournalDescription());
            journalService.saveEntry(oldEntry);
            return new ResponseEntity<>(oldEntry, HttpStatus.OK);
        }
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
