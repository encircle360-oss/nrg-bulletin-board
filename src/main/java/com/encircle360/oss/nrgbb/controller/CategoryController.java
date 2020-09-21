package com.encircle360.oss.nrgbb.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.encircle360.oss.nrgbb.dto.category.CategoryDTO;
import com.encircle360.oss.nrgbb.dto.category.CreateUpdateCategoryDTO;
import com.encircle360.oss.nrgbb.dto.pagination.PageContainer;
import com.encircle360.oss.nrgbb.dto.pagination.PageContainerFactory;
import com.encircle360.oss.nrgbb.mapper.CategoryMapper;
import com.encircle360.oss.nrgbb.model.Category;
import com.encircle360.oss.nrgbb.service.CategoryService;
import com.encircle360.oss.nrgbb.service.ThreadService;

import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final ThreadService threadService;

    private final CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    private final PageContainerFactory<CategoryDTO> pageContainerFactory = new PageContainerFactory<>();

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PageContainer<CategoryDTO>> getAll(@RequestParam(required = false) final Integer size,
                                                             @RequestParam(required = false) final Integer page,
                                                             @RequestParam(required = false) final String sort) {
        Pageable pageable = pageContainerFactory.mapRequestToPageable(size, page, sort);
        Page<Category> categoryPage = categoryService.getAll(pageable);
        List<CategoryDTO> categoryDtos = categoryMapper.toDtos(categoryPage.getContent());

        PageContainer<CategoryDTO> pageContainer = pageContainerFactory.getPageContainer(pageable, categoryPage, categoryDtos);

        return ResponseEntity.status(HttpStatus.OK).body(pageContainer);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDTO> get(@PathVariable final String id) {
        Category category = categoryService.get(id);
        if (category == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        CategoryDTO categoryDTO = categoryMapper.toDto(category);

        return ResponseEntity.status(HttpStatus.OK).body(categoryDTO);
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDTO> create(@RequestBody @Valid final CreateUpdateCategoryDTO createUpdateCategoryDTO) {
        Category category = categoryMapper.createFromDto(createUpdateCategoryDTO);
        category = categoryService.save(category);
        CategoryDTO categoryDto = categoryMapper.toDto(category);

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryDto);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDTO> update(@PathVariable final String id, @RequestBody @Valid final CreateUpdateCategoryDTO createUpdateCategoryDTO) {
        Category category = categoryService.get(id);
        if (category == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        categoryMapper.updateFromDto(createUpdateCategoryDTO, category);
        category = categoryService.save(category);
        CategoryDTO categoryDTO = categoryMapper.toDto(category);

        return ResponseEntity.status(HttpStatus.OK).body(categoryDTO);
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable final String id) {
        Category category = categoryService.get(id);
        if (category == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        long threadCount = threadService.countByCategoryId(id);

        if (threadCount > 0) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        categoryService.delete(category);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

