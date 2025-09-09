package com.journal.journalApp.repository;

import com.journal.journalApp.entity.journalEntry;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface journalRapository extends MongoRepository<journalEntry, String> {

}
