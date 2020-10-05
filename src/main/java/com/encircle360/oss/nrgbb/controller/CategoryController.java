package com.encircle360.oss.nrgbb.controller;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
import com.encircle360.oss.nrgbb.security.Roles;
import com.encircle360.oss.nrgbb.service.CategoryService;
import com.encircle360.oss.nrgbb.service.ThreadService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final ThreadService threadService;

    private final CategoryMapper categoryMapper = CategoryMapper.INSTANCE;

    private final PageContainerFactory<CategoryDTO> pageContainerFactory = new PageContainerFactory<>();

    @Secured(Roles.Category.CAN_LIST)
    @GetMapping(value = "", produces = APPLICATION_JSON_VALUE)
    @Operation(operationId = "getAllCategories", description = "returns all categories in a pageable way")
    public ResponseEntity<PageContainer<CategoryDTO>> getAll(@RequestParam(required = false) final Integer size,
                                                             @RequestParam(required = false) final Integer page,
                                                             @RequestParam(required = false) final String sort) {
        Pageable pageable = pageContainerFactory.mapRequestToPageable(size, page, sort);
        Page<Category> categoryPage = categoryService.getAll(pageable);
        List<CategoryDTO> categoryDtos = categoryMapper.toDtos(categoryPage.getContent());

        PageContainer<CategoryDTO> pageContainer = pageContainerFactory.getPageContainer(pageable, categoryPage, categoryDtos);

        return ResponseEntity.status(HttpStatus.OK).body(pageContainer);
    }

    @Secured(Roles.Category.CAN_GET)
    @GetMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @Operation(operationId = "getCategory", description = "gets one category by its id")
    public ResponseEntity<CategoryDTO> get(@PathVariable final String id) {
        Category category = categoryService.get(id);
        if (category == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        CategoryDTO categoryDTO = categoryMapper.toDto(category);

        return ResponseEntity.status(HttpStatus.OK).body(categoryDTO);
    }

    @Secured(Roles.Category.CAN_CREATE)
    @Operation(operationId = "createCategory", description = "creates a category")
    @PostMapping(value = "", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<CategoryDTO> create(@RequestBody @Valid final CreateUpdateCategoryDTO createUpdateCategoryDTO) {
        Category category = categoryMapper.createFromDto(createUpdateCategoryDTO);
        category = categoryService.save(category);
        CategoryDTO categoryDto = categoryMapper.toDto(category);

        return ResponseEntity.status(HttpStatus.CREATED).body(categoryDto);
    }

    @Secured(Roles.Category.CAN_UPDATE)
    @Operation(operationId = "updateCategory", description = "updates a category by its id")
    @PutMapping(value = "/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
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

    @Secured(Roles.Category.CAN_DELETE)
    @DeleteMapping(value = "/{id}", produces = APPLICATION_JSON_VALUE)
    @Operation(operationId = "deleteCategory", description = "deletes a category by the given id")
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

