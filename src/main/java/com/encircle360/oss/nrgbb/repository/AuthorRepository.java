package com.encircle360.oss.nrgbb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.encircle360.oss.nrgbb.model.Author;

@Repository
public interface AuthorRepository extends MongoRepository<Author, String> {
    long countByEmail(String email);
}
