package com.alelo.resources.adapter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.alelo.resources.adapter.CategoryService;
import com.alelo.resources.dto.CategoryDTO;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public ResponseEntity<CategoryDTO> updateCategory(CategoryDTO category) {
        return this.restTemplate.postForEntity("localhost:8080/anyUrlToTest", category, CategoryDTO.class);
    }
}
