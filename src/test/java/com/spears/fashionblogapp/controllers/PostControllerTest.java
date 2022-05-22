package com.spears.fashionblogapp.controllers;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spears.fashionblogapp.dto.CommentDto;
import com.spears.fashionblogapp.dto.PostDto;
import com.spears.fashionblogapp.services.PostServices;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
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

@ContextConfiguration(classes = {PostController.class})
@ExtendWith(MockitoExtension.class)
class PostControllerTest {
    @Autowired
    private PostController postController;

    @Mock
    private PostServices postServices;

    @Test
    void testCreatePost() throws Exception {
        when(this.postServices.createPost((PostDto) any(), (Long) any(), (Long) any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));

        PostDto postDto = new PostDto();
        postDto.setContent("Quality is not an act, it is a habit");
        postDto.setId(123L);
        postDto.setTitle("Agbada with the swag");

        String content = (new ObjectMapper()).writeValueAsString(postDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/post/{userId}/{categoryId}/create", 123L, 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.postController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testEditPost() throws Exception {
        when(this.postServices.editPost((PostDto) any(), (Long) any(), (Long) any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));

        PostDto postDto = new PostDto();
        postDto.setContent("Quality is not an act, it is a habit");
        postDto.setId(123L);
        postDto.setTitle("Agbada with the swag");
        String content = (new ObjectMapper()).writeValueAsString(postDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/post/{id}/{userId}/edit", 123L, 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.postController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testDeletePost() throws Exception {
        when(this.postServices.deletePost((Long) any(), (Long) any()))
                .thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/post/{postId}/{userId}/delete", 123L,
                123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.postController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testLikePost() throws Exception {
        doNothing().when(this.postServices).likePost((Long) any(), (Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/post/{postId}/{userId}/like", 123L,
                123L);
        MockMvcBuilders.standaloneSetup(this.postController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Liked"));
    }

    @Test
    void testUnlikePost() throws Exception {
        doNothing().when(this.postServices).likePost((Long) any(), (Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/post/{postId}/{userId}/unlike", 123L,
                123L);
        MockMvcBuilders.standaloneSetup(this.postController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Unliked"));
    }


    @Test
    void testDeleteComment() throws Exception {
        doNothing().when(this.postServices).deleteComment((Long) any(), (Long) any(), (Long) any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/post/{postId}/{commentId}/{userId}/delete-comment", 123L, 123L, 123L);
        MockMvcBuilders.standaloneSetup(this.postController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("Comment has been deleted!"));
    }

    @Test
    void testGetAllPosts() throws Exception {
        when(this.postServices.fetchPosts()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/post/view-all");
        MockMvcBuilders.standaloneSetup(this.postController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    @Test
    void testGetPost() throws Exception {
        when(this.postServices.fetchPost((Long) any())).thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/post/view/{id}", 123L);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.postController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }

    @Test
    void testMakeCommentOnPost() throws Exception {
        when(this.postServices.commentOnAPost((CommentDto) any(), (Long) any(), (Long) any())).thenReturn(new CommentDto());

        CommentDto commentDto = new CommentDto();
        commentDto.setCommentContent("Not all who wander are lost");
        commentDto.setCommentId(123L);
        String content = (new ObjectMapper()).writeValueAsString(commentDto);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/post/{postId}/{userId}/comment", 123L, 123L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        MockMvcBuilders.standaloneSetup(this.postController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("text/plain;charset=ISO-8859-1"))
                .andExpect(MockMvcResultMatchers.content().string("User comment saved."));
    }
}

