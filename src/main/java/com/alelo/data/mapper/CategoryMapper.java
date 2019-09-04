package com.alelo.data.mapper;

import org.springframework.stereotype.Component;

import com.alelo.data.Category;
import com.alelo.resources.dto.CategoryDTO;
import com.alelo.utils.Converter;

@Component
public class CategoryMapper implements Converter<Category, CategoryDTO> {

    @Override
    public Category entityFromDto(CategoryDTO dto) throws Exception {
        Category category = new Category();
        category.setCategoryId(dto.getId());
        category.setCategoryName(dto.getName());
        return category;
    }

    @Override
    public CategoryDTO dtoFromEntity(Category entity) throws Exception {
        CategoryDTO categoryDto = new CategoryDTO();
        categoryDto.setName(entity.getCategoryName());
        categoryDto.setId(entity.getCategoryId());
        return categoryDto;
    }

}
