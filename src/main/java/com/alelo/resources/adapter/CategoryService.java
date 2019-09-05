package com.alelo.resources.adapter;

import org.springframework.http.ResponseEntity;

import com.alelo.resources.dto.CategoryDTO;

public interface CategoryService {

    ResponseEntity<CategoryDTO> updateCategory(CategoryDTO categoryDto);

}
