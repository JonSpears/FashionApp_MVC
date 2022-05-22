package com.spears.fashionblogapp.services.servicesimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.spears.fashionblogapp.dto.CommentDto;
import com.spears.fashionblogapp.dto.PostDto;
import com.spears.fashionblogapp.entities.BlogUser;
import com.spears.fashionblogapp.entities.Category;
import com.spears.fashionblogapp.entities.Comment;
import com.spears.fashionblogapp.entities.Likes;
import com.spears.fashionblogapp.entities.Post;
import com.spears.fashionblogapp.exceptions.InvalidRequestException;
import com.spears.fashionblogapp.exceptions.PostDoesNotExistException;
import com.spears.fashionblogapp.exceptions.UserDoesNotExistException;
import com.spears.fashionblogapp.repositories.BlogUserRepository;
import com.spears.fashionblogapp.repositories.CategoryRepository;
import com.spears.fashionblogapp.repositories.CommentRepository;
import com.spears.fashionblogapp.repositories.LikesRepository;
import com.spears.fashionblogapp.repositories.PostRepository;

import java.sql.Date;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {PostServicesImpl.class})
@ExtendWith(SpringExtension.class)
class PostServicesImplTest {
    @MockBean
    private BlogUserRepository blogUserRepository;

    @MockBean
    private CategoryRepository categoryRepository;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private LikesRepository likesRepository;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private PostRepository postRepository;

    @Autowired
    private PostServicesImpl postServicesImpl;

    @Test
    void testCreatePost() {
        BlogUser blogUser = new BlogUser();
        blogUser.setCategories(new ArrayList<>());
        blogUser.setEmail("nedu@spears.org");
        blogUser.setLikes(new ArrayList<>());
        blogUser.setName("Nedu");
        blogUser.setPassword("nedu123");
        blogUser.setPosts(new ArrayList<>());
        blogUser.setRole("User");
        blogUser.setUserId(123L);
        blogUser.setUsername("Nedu");
        Optional<BlogUser> ofResult = Optional.of(blogUser);
        when(this.blogUserRepository.findById((Long) any())).thenReturn(ofResult);
        assertThrows(InvalidRequestException.class, () -> this.postServicesImpl.createPost(new PostDto(), 123L, 123L));
        verify(this.blogUserRepository).findById((Long) any());
    }

    @Test
    void testEditPost() {
        when(this.postRepository.findById((Long) any())).thenReturn(Optional.empty());

        BlogUser blogUser = new BlogUser();
        blogUser.setCategories(new ArrayList<>());
        blogUser.setEmail("jane.doe@example.org");
        blogUser.setLikes(new ArrayList<>());
        blogUser.setName("Name");
        blogUser.setPassword("iloveyou");
        blogUser.setPosts(new ArrayList<>());
        blogUser.setRole("Role");
        blogUser.setUserId(123L);
        blogUser.setUsername("janedoe");
        Optional<BlogUser> ofResult = Optional.of(blogUser);
        when(this.blogUserRepository.findById((Long) any())).thenReturn(ofResult);
        assertThrows(PostDoesNotExistException.class, () -> this.postServicesImpl.editPost(new PostDto(), 123L, 123L));
        verify(this.postRepository).findById((Long) any());
    }

    @Test
    void testFetchPosts() {
        when(this.postRepository.findAll()).thenReturn(new ArrayList<>());
        assertTrue(this.postServicesImpl.fetchPosts().isEmpty());
        verify(this.postRepository).findAll();
    }

    @Test
    void testDeletePost() {
        BlogUser blogUser = new BlogUser();
        blogUser.setCategories(new ArrayList<>());
        blogUser.setEmail("spears@spears.org");
        blogUser.setLikes(new ArrayList<>());
        blogUser.setName("Spears");
        blogUser.setPassword("spr123");
        blogUser.setPosts(new ArrayList<>());
        blogUser.setRole("Admin");
        blogUser.setUserId(123L);
        blogUser.setUsername("JonSpears");

        Category category = new Category();
        category.setBlogUser(blogUser);
        category.setCategoryDescription("Category Description");
        category.setCategoryId(123L);
        category.setCategoryName("Category Name");
        category.setPost(new ArrayList<>());

        Post post = new Post();
        post.setBlogUser(blogUser);
        post.setCategory(category);
        post.setId(123L);
        post.setNumberOfComments(10);
        post.setNumberOfLikes(10);
        post.setPostComments(new ArrayList<>());
        post.setPostContent("Quality is not an act, it is a habit");
        post.setPostDate(mock(Date.class));
        post.setPostLikes(new ArrayList<>());
        post.setPostTitle("Agbada with the swag");
        post.setTime(mock(Time.class));
        Optional<Post> ofResult = Optional.of(post);
        when(this.postRepository.findById((Long) any())).thenReturn(ofResult);

        BlogUser blogUser2 = new BlogUser();
        blogUser2.setCategories(new ArrayList<>());
        blogUser2.setEmail("johnny@spears.org");
        blogUser2.setLikes(new ArrayList<>());
        blogUser2.setName("Johnny");
        blogUser2.setPassword("john123");
        blogUser2.setPosts(new ArrayList<>());
        blogUser2.setRole("User");
        blogUser2.setUserId(123L);
        blogUser2.setUsername("JohnnyO");
        Optional<BlogUser> ofResult1 = Optional.of(blogUser2);
        when(this.blogUserRepository.findById((Long) any())).thenReturn(ofResult1);
        assertThrows(InvalidRequestException.class, () -> this.postServicesImpl.deletePost(123L, 123L));
        verify(this.postRepository).findById((Long) any());
        verify(this.blogUserRepository).findById((Long) any());
    }


    @Test
    void testFetchLikesPerPost() {
        when(this.likesRepository.findAllByPost_Id((Long) any())).thenThrow(new InvalidRequestException("foo"));
        assertThrows(InvalidRequestException.class, () -> this.postServicesImpl.fetchLikesPerPost(123L));
        verify(this.likesRepository).findAllByPost_Id((Long) any());
    }

    @Test
    void testCommentOnAPost() {
        BlogUser blogUser = new BlogUser();
        blogUser.setCategories(new ArrayList<>());
        blogUser.setEmail("ruth@spears.org");
        blogUser.setLikes(new ArrayList<>());
        blogUser.setName("Ruth");
        blogUser.setPassword("ruth123");
        blogUser.setPosts(new ArrayList<>());
        blogUser.setRole("User");
        blogUser.setUserId(123L);
        blogUser.setUsername("RuthW");

        BlogUser blogUser1 = new BlogUser();
        blogUser1.setCategories(new ArrayList<>());
        blogUser1.setEmail("jane@spears.org");
        blogUser1.setLikes(new ArrayList<>());
        blogUser1.setName("Jane");
        blogUser1.setPassword("jane123");
        blogUser1.setPosts(new ArrayList<>());
        blogUser1.setRole("User");
        blogUser1.setUserId(123L);
        blogUser1.setUsername("JaneMike");

        Category category = new Category();
        category.setBlogUser(blogUser1);
        category.setCategoryDescription("Category Description");
        category.setCategoryId(123L);
        category.setCategoryName("Category Name");
        category.setPost(new ArrayList<>());

        Post post = new Post();
        post.setBlogUser(blogUser);
        post.setCategory(category);
        post.setId(123L);
        post.setNumberOfComments(10);
        post.setNumberOfLikes(10);
        post.setPostComments(new ArrayList<>());
        post.setPostContent("Quality is not an act, it is a habit");
        post.setPostDate(mock(java.sql.Date.class));
        post.setPostLikes(new ArrayList<>());
        post.setPostTitle("Agbada with the swag");
        post.setTime(mock(Time.class));
        when(this.postRepository.getById((Long) any())).thenReturn(post);

        BlogUser blogUser2 = new BlogUser();
        blogUser2.setCategories(new ArrayList<>());
        blogUser2.setEmail("isioma@spears.org");
        blogUser2.setLikes(new ArrayList<>());
        blogUser2.setName("Isioma");
        blogUser2.setPassword("oma123");
        blogUser2.setPosts(new ArrayList<>());
        blogUser2.setRole("User");
        blogUser2.setUserId(123L);
        blogUser2.setUsername("Isioma");

        BlogUser blogUser3 = new BlogUser();
        blogUser3.setCategories(new ArrayList<>());
        blogUser3.setEmail("ihaza@spears.org");
        blogUser3.setLikes(new ArrayList<>());
        blogUser3.setName("Ihaza");
        blogUser3.setPassword("iamme123");
        blogUser3.setPosts(new ArrayList<>());
        blogUser3.setRole("User");
        blogUser3.setUserId(123L);
        blogUser3.setUsername("Ihaza");

        BlogUser blogUser4 = new BlogUser();
        blogUser4.setCategories(new ArrayList<>());
        blogUser4.setEmail("johnny@spears.org");
        blogUser4.setLikes(new ArrayList<>());
        blogUser4.setName("Johnny");
        blogUser4.setPassword("jny123");
        blogUser4.setPosts(new ArrayList<>());
        blogUser4.setRole("User");
        blogUser4.setUserId(123L);
        blogUser4.setUsername("JohnnyO");

        Category category1 = new Category();
        category1.setBlogUser(blogUser4);
        category1.setCategoryDescription("Category Description");
        category1.setCategoryId(123L);
        category1.setCategoryName("Category Name");
        category1.setPost(new ArrayList<>());

        Post post1 = new Post();
        post1.setBlogUser(blogUser3);
        post1.setCategory(category1);
        post1.setId(123L);
        post1.setNumberOfComments(10);
        post1.setNumberOfLikes(10);
        post1.setPostComments(new ArrayList<>());
        post1.setPostContent("Quality is not an act, it is a habit");
        post1.setPostDate(mock(java.sql.Date.class));
        post1.setPostLikes(new ArrayList<>());
        post1.setPostTitle("Agbada with the swag");
        post1.setTime(mock(Time.class));

        Comment comment = new Comment();
        comment.setBlogUser(blogUser2);
        comment.setCommentContent("Quality is not an act, it is a habit");
        LocalDateTime atStartOfDayResult = LocalDate.of(1970, 1, 1).atStartOfDay();
        comment.setCommentDate(java.util.Date.from(atStartOfDayResult.atZone(ZoneId.of("UTC")).toInstant()));
        comment.setCommentId(123L);
        comment.setCommentTime(mock(Time.class));
        comment.setPost(post1);
        when(this.commentRepository.save((Comment) any())).thenReturn(comment);

        BlogUser blogUser5 = new BlogUser();
        blogUser5.setCategories(new ArrayList<>());
        blogUser5.setEmail("dammi@spears.org");
        blogUser5.setLikes(new ArrayList<>());
        blogUser5.setName("Dammy");
        blogUser5.setPassword("damn123");
        blogUser5.setPosts(new ArrayList<>());
        blogUser5.setRole("User");
        blogUser5.setUserId(123L);
        blogUser5.setUsername("Dammy1");
        when(this.blogUserRepository.getById((Long) any())).thenReturn(blogUser5);
        CommentDto actualCommentOnAPostResult = this.postServicesImpl.commentOnAPost(new CommentDto(), 123L, 123L);
        assertNull(actualCommentOnAPostResult.getCommentContent());
        assertNull(actualCommentOnAPostResult.getCommentId());
        verify(this.postRepository, atLeast(1)).getById((Long) any());
        verify(this.commentRepository).save((Comment) any());
        verify(this.blogUserRepository).getById((Long) any());
    }
}