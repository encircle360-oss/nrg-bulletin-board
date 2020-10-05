package com.encircle360.oss.nrgbb.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.encircle360.oss.nrgbb.model.Post;
import com.encircle360.oss.nrgbb.repository.PostRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public Page<Post> getAll(String threadId, String authorId, Pageable pageable) {
        if (authorId == null && threadId == null) {
            return postRepository.findAll(pageable);
        }

        if (threadId != null && authorId == null) {
            return postRepository.findAllByThreadId(threadId, pageable);
        }

        if (threadId == null) {
            return postRepository.findAllByAuthorId(threadId, pageable);
        }
        return postRepository.findAllByAuthorIdAndThreadId(authorId, threadId, pageable);
    }

    public Post get(String id) {
        return postRepository.findById(id).orElse(null);
    }

    public Post save(Post post) {
        return postRepository.save(post);
    }

    public void delete(Post post) {
        postRepository.delete(post);
    }

    public void deleteByThreadId(String threadId) {
        postRepository.deleteByThreadId(threadId);
    }
}
