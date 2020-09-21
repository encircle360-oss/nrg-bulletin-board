package com.encircle360.oss.nrgbb.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.encircle360.oss.nrgbb.model.Category;
import com.encircle360.oss.nrgbb.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryReposity;

    public Page<Category> getAll(Pageable pageable) {
        return categoryReposity.findAll(pageable);
    }

    public Category get(String id) {
        return categoryReposity.findById(id).orElse(null);
    }

    public Category save(Category category) {
        return categoryReposity.save(category);
    }

    public void delete(Category category) {
        categoryReposity.delete(category);
    }
}