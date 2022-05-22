package com.spears.fashionblogapp.services.servicesimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.spears.fashionblogapp.dto.CategoryDto;
import com.spears.fashionblogapp.entities.BlogUser;
import com.spears.fashionblogapp.entities.Category;
import com.spears.fashionblogapp.entities.Likes;
import com.spears.fashionblogapp.entities.Post;
import com.spears.fashionblogapp.exceptions.CategoryDoesNotExist;
import com.spears.fashionblogapp.exceptions.InvalidRequestException;
import com.spears.fashionblogapp.exceptions.UserDoesNotExistException;
import com.spears.fashionblogapp.repositories.BlogUserRepository;
import com.spears.fashionblogapp.repositories.CategoryRepository;
import com.spears.fashionblogapp.repositories.PostRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CategoryServicesImpl.class})
@ExtendWith(SpringExtension.class)
class CategoryServicesImplTest {
    @MockBean
    private BlogUserRepository blogUserRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryServicesImpl categoryServicesImpl;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private PostRepository postRepository;

    @Test
    void testThatExceptionIsThrownWhenUserCreateCategory() {
        BlogUser blogUser = new BlogUser();
        blogUser.setCategories(new ArrayList<>());
        blogUser.setEmail("sinebi@spears.org");
        blogUser.setLikes(new ArrayList<>());
        blogUser.setName("Sinebi");
        blogUser.setPassword("long123");
        blogUser.setPosts(new ArrayList<>());
        blogUser.setRole("User");
        blogUser.setUserId(123L);
        blogUser.setUsername("SinebiI");
        Optional<BlogUser> ofResult = Optional.of(blogUser);
        when(this.blogUserRepository.findById((Long) any())).thenReturn(ofResult);
        assertThrows(InvalidRequestException.class,
                () -> this.categoryServicesImpl.createCategory(new CategoryDto(), 123L));
        verify(this.blogUserRepository).findById((Long) any());
    }

    @Test
    void testThatExceptionIsThrownWhenUserFetchCategory() {
        when(this.categoryRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(CategoryDoesNotExist.class,
                () -> this.categoryServicesImpl.fetchCategory(123L));
        verify(this.categoryRepository).findById((Long) any());
    }


    @Test
    void testThatExceptionIsThrownWhenUserFetchAllCategories() {
        when(this.categoryRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(CategoryDoesNotExist.class,
                () -> this.categoryServicesImpl.fetchCategories());
        verify(this.categoryRepository).findAll();
    }

    @Test
    void testThatExceptionIsThrownWhenUserFetchCategoryWithInvalidRequest() {
        when(this.categoryRepository.findById((Long) any())).thenReturn(Optional.empty());
        assertThrows(CategoryDoesNotExist.class,
                () -> this.categoryServicesImpl.fetchCategory(123L));
        verify(this.categoryRepository).findById((Long) any());
    }

    @Test
    void testThatExceptionIsThrownWhenUserFetchAllCategoriesWithInvalidRequest() {
        when(this.categoryRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(CategoryDoesNotExist.class,
                () -> this.categoryServicesImpl.fetchCategories(),"There are no categories.");
        verify(this.categoryRepository).findAll();
    }
}

