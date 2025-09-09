package com.journal.journalApp.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "entry")
@Data
public class journalEntry {
    @Id
    private String journalId;
    private LocalDateTime date;
    private String journalName;
    private String journalDescription;
}
