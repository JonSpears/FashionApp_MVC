package com.spears.fashionblogapp.controllers;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spears.fashionblogapp.dto.BlogUSerDto;
import com.spears.fashionblogapp.services.BlogUserServices;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {BlogUserController.class})
@ExtendWith(MockitoExtension.class)
@WebMvcTest
class BlogUserControllerTest {
    @Autowired
    private MockMvc blogUserController;

    @Mock
    private BlogUserServices blogUserServices;

    @Test
    void testCreateUser() throws Exception {
        when(this.blogUserServices.createUser((BlogUSerDto) any())).thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));

        BlogUSerDto blogUSerDto = new BlogUSerDto();
        blogUSerDto.setEmail("spears@spears.org");
        blogUSerDto.setId(123L);
        blogUSerDto.setName("Spears");
        blogUSerDto.setPassword("spr12345");
        blogUSerDto.setUsername("JonSpears");
        String content = (new ObjectMapper()).writeValueAsString(blogUSerDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/create-user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.blogUserController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testCreateUserAdmin() throws Exception {
        when(this.blogUserServices.createAdmin((BlogUSerDto) any())).thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));

        BlogUSerDto blogUSerDto = new BlogUSerDto();
        blogUSerDto.setEmail("admin@spears.org");
        blogUSerDto.setId(123L);
        blogUSerDto.setName("Spears");
        blogUSerDto.setPassword("hnt12345");
        blogUSerDto.setUsername("AdminSpears");
        String content = (new ObjectMapper()).writeValueAsString(blogUSerDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/create-admin")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.blogUserController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }
}

