package com.encircle360.oss.nrgbb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.encircle360.oss.nrgbb.model.Thread;

@Repository
public interface ThreadRepository extends MongoRepository<Thread, String> {
}
