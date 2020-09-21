package com.encircle360.oss.nrgbb.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.encircle360.oss.nrgbb.model.Thread;
import com.encircle360.oss.nrgbb.repository.ThreadRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ThreadService {

    private final ThreadRepository threadRepository;

    public Page<Thread> getAll(String authorId, String categoryId, Pageable pageable) {
        if (authorId == null && categoryId == null) {
            return threadRepository.findAll(pageable);
        }

        if (authorId != null && categoryId == null) {
            return threadRepository.findAllByAuthorId(authorId, pageable);
        }

        if (authorId == null) {
            return threadRepository.findAllByCategoryId(categoryId, pageable);
        }
        return threadRepository.findAllByAuthorIdAndCategoryId(authorId, categoryId, pageable);
    }

    public Thread get(final String id) {
        return threadRepository.findById(id).orElse(null);
    }

    public Thread save(Thread thread) {
        return threadRepository.save(thread);
    }

    public void delete(Thread thread) {
        threadRepository.delete(thread);
    }

    public long countByCategoryId(String id) {
        return threadRepository.countByCategoryId(id);
    }
}
