package com.spears.fashionblogapp.services.servicesimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.spears.fashionblogapp.dto.BlogUSerDto;
import com.spears.fashionblogapp.entities.BlogUser;
import com.spears.fashionblogapp.repositories.BlogUserRepository;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {BlogUserServicesImpl.class})
@ExtendWith(SpringExtension.class)
class BlogUserServicesImplTest {
    @MockBean
    private BlogUserRepository blogUserRepository;

    @Autowired
    private BlogUserServicesImpl blogUserServicesImpl;

    @Test
    void testCreateUser2() {
        BlogUser blogUser = new BlogUser();
        blogUser.setEmail("sinebi@spears.org");
        blogUser.setLikes(new ArrayList<>());
        blogUser.setName("Sinebi");
        blogUser.setPassword("isineb123");
        blogUser.setPosts(new ArrayList<>());
        blogUser.setRole("User");
        blogUser.setUserId(123L);
        blogUser.setUsername("SinebiISinebi");
        when(this.blogUserRepository.save((BlogUser) any())).thenReturn(blogUser);
        this.blogUserServicesImpl.createUser(new BlogUSerDto());
    }

    @Test
    void testCreateAdmin2() {
        BlogUser blogUser = new BlogUser();
        blogUser.setEmail("spears@spears.org");
        blogUser.setLikes(new ArrayList<>());
        blogUser.setName("Spears");
        blogUser.setPassword("johan123");
        blogUser.setPosts(new ArrayList<>());
        blogUser.setRole("Admin");
        blogUser.setUserId(123L);
        blogUser.setUsername("JonSpears");
        when(this.blogUserRepository.save((BlogUser) any())).thenReturn(blogUser);
        this.blogUserServicesImpl.createAdmin(new BlogUSerDto());
    }
}

