package com.spears.fashionblogapp.controllers;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spears.fashionblogapp.dto.CategoryDto;
import com.spears.fashionblogapp.services.CategoryServices;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {CategoryController.class})
@ExtendWith(SpringExtension.class)
class CategoryControllerTest {
    @Autowired
    private CategoryController categoryController;

    @MockBean
    private CategoryServices categoryServices;

    @Test
    void testCreateCategory() throws Exception {
        when(this.categoryServices.createCategory((CategoryDto) any(), (Long) any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryDescription("Category Description");
        categoryDto.setCategoryName("Category Name");
        categoryDto.setId(123L);
        String content = (new ObjectMapper()).writeValueAsString(categoryDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/category/{userId}/create", 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders
                .standaloneSetup(this.categoryController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testEditCategory() throws Exception {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryDescription("Category Description");
        categoryDto.setCategoryName("Category Name");
        categoryDto.setId(123L);
        String content = (new ObjectMapper()).writeValueAsString(categoryDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/category/{categoryId}/{userId}/edit", 123L, 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.categoryController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void testGetCategories() throws Exception {
        when(this.categoryServices.fetchCategories())
                .thenReturn(new ArrayList<>());

        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/category/view-all", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.categoryController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(200));
    }


    @Test
    void testViewAllCategories() throws Exception {
        when(this.categoryServices.fetchCategories()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/category/view-all");
        MockMvcBuilders.standaloneSetup(this.categoryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testViewAllCategories2() throws Exception {
        when(this.categoryServices.fetchCategories()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/category/view-all");
        getResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.categoryController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testViewAllCategories3() throws Exception {
        when(this.categoryServices.fetchCategories()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/category/view-all");
        MockMvcBuilders.standaloneSetup(this.categoryController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testViewAllCategories4() throws Exception {
        when(this.categoryServices.fetchCategories()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/category/view-all");
        getResult.contentType("https://example.org/example");
        MockMvcBuilders.standaloneSetup(this.categoryController)
                .build()
                .perform(getResult)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testViewCategoryById() throws Exception {
        when(this.categoryServices.fetchCategory((Long) any())).thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/category/{categoryId}/view", 123L);
        ResultActions actualPerformResult = MockMvcBuilders
                .standaloneSetup(this.categoryController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testViewCategoryById2() throws Exception {
        when(this.categoryServices.fetchCategory((Long) any())).thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders
                .get("/category/{categoryId}/view", 123L);
        getResult.contentType("https://example.org/example");
        ResultActions actualPerformResult = MockMvcBuilders
                .standaloneSetup(this.categoryController)
                .build()
                .perform(getResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testViewCategoryById3() throws Exception {
        when(this.categoryServices.fetchCategory((Long) any())).thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/category/{categoryId}/view", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.categoryController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testViewCategoryById4() throws Exception {
        when(this.categoryServices.fetchCategory((Long) any())).thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        MockHttpServletRequestBuilder getResult = MockMvcRequestBuilders.get("/category/{categoryId}/view", 123L);
        getResult.contentType("https://example.org/example");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.categoryController)
                .build()
                .perform(getResult);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }
}

