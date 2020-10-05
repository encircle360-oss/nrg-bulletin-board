package com.encircle360.oss.nrgbb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.encircle360.oss.nrgbb.model.Thread;

@Repository
public interface ThreadRepository extends MongoRepository<Thread, String> {

    long countByCategoryId(String id);

    Page<Thread> findAllByAuthorId(String authorId, Pageable pageable);

    Page<Thread> findAllByCategoryId(String category, Pageable pageable);

    Page<Thread> findAllByAuthorIdAndCategoryId(String authorId, String categoryId, Pageable pageable);
}
