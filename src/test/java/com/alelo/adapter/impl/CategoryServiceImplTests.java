package com.alelo.adapter.impl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.alelo.Application;
import com.alelo.resources.adapter.impl.CategoryServiceImpl;
import com.alelo.resources.dto.CategoryDTO;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class CategoryServiceImplTests {

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private RestTemplate restTemplateMock;

    @Before
    public void before() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void updateCategoryTest() {
        CategoryDTO categoryDto = givenCategory();
        givenRestPostWithCategoryDTO(categoryDto);
        ResponseEntity<CategoryDTO> updatedCategory = whenUpdateCategory(categoryDto);
        thenStatusIsOk(updatedCategory);
    }

    private void givenRestPostWithCategoryDTO(CategoryDTO categoryDto) {
        Mockito.when(this.restTemplateMock.postForEntity("localhost:8080/anyUrlToTest", categoryDto, CategoryDTO.class)).thenReturn(new ResponseEntity<CategoryDTO>(HttpStatus.OK));
    }

    private CategoryDTO givenCategory() {
        CategoryDTO categoryDto = new CategoryDTO();
        categoryDto.setName("Category name");
        return categoryDto;
    }

    private ResponseEntity<CategoryDTO> whenUpdateCategory(CategoryDTO categoryDto) {
        return this.categoryService.updateCategory(categoryDto);
    }

    private void thenStatusIsOk(ResponseEntity<?> responseEntity) {
        Assert.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

}
