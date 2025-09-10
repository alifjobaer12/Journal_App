package com.journal.journalApp.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "entry")
@Data
@NoArgsConstructor
public class journalEntry {
    @Id
    private ObjectId journalId;

    private LocalDateTime date;
    @NonNull

    private String journalName;

    private String journalDescription;
}
