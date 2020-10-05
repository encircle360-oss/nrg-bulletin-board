package com.encircle360.oss.nrgbb.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.encircle360.oss.nrgbb.dto.category.CategoryDTO;
import com.encircle360.oss.nrgbb.dto.category.CreateUpdateCategoryDTO;
import com.encircle360.oss.nrgbb.model.Category;
import com.encircle360.oss.nrgbb.util.StringUtils;

@Mapper
public interface CategoryMapper {

    public static final CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDTO toDto(Category category);

    List<CategoryDTO> toDtos(List<Category> categories);

    Category createFromDto(CreateUpdateCategoryDTO createUpdateCategoryDTO);

    void updateFromDto(CreateUpdateCategoryDTO createUpdateCategoryDTO, @MappingTarget Category category);

    @AfterMapping
    default void afterFromDto(@MappingTarget Category category) {
        category.setName(StringUtils.noHtml(category.getName()));
    }
}
