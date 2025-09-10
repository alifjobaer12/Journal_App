package com.journal.journalApp.repository;

import com.journal.journalApp.entity.user;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface userRapository extends MongoRepository<user, ObjectId> {
    user findByUserName(String userName);
}
