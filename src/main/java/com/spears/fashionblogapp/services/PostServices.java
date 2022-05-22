package com.spears.fashionblogapp.services;

import com.spears.fashionblogapp.dto.CommentDto;
import com.spears.fashionblogapp.dto.PostDto;
import com.spears.fashionblogapp.entities.Likes;
import com.spears.fashionblogapp.entities.Post;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PostServices {
    ResponseEntity<String> createPost(PostDto postDto, Long userId, Long categoryId);

    ResponseEntity<String> editPost(PostDto postDto, Long id, Long userId);

    ResponseEntity<PostDto> fetchPost(Long postId);

    List<PostDto> fetchPosts();

    ResponseEntity<String> deletePost(Long postId, Long userId);

    void likePost(Long postId, Long userId);

    List<Likes> fetchLikesPerPost(Long postId);

    CommentDto commentOnAPost(final CommentDto commentDto, final Long postId, final Long userId);

    void deleteComment(Long postId, Long commentId, Long userId);
}
