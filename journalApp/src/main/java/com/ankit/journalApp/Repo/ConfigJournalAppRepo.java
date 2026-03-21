package com.ankit.journalApp.Repo;

import com.ankit.journalApp.entity.ConfigJournalAppEntity;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.annotation.Collation;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface ConfigJournalAppRepo extends MongoRepository<ConfigJournalAppEntity, ObjectId> {
}
