package com.encircle360.oss.nrgbb.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.encircle360.oss.nrgbb.model.Post;

@Repository
public interface PostRepository extends MongoRepository<Post, String> {

    Page<Post> findAllByAuthorId(String threadId, Pageable pageable);

    Page<Post> findAllByThreadId(String threadId, Pageable pageable);

    Page<Post> findAllByAuthorIdAndThreadId(String authorId, String threadId, Pageable pageable);

    void deleteByThreadId(String threadId);
}
