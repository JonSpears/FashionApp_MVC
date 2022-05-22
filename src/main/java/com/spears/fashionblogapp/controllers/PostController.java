package com.spears.fashionblogapp.controllers;

import com.spears.fashionblogapp.dto.CommentDto;
import com.spears.fashionblogapp.dto.PostDto;
import com.spears.fashionblogapp.services.PostServices;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/post")
public class PostController {


    private final PostServices postServices;

    @Autowired
    public PostController(PostServices postServices) {
        this.postServices = postServices;
    }

    @PostMapping("/{userId}/{categoryId}/create")
    public ResponseEntity<String> createPost (
            @PathVariable(value = "userId")Long userId,
            @PathVariable(value = "categoryId")Long categoryId,
            @RequestBody PostDto postDto
    ) {
        return postServices.createPost(postDto, userId, categoryId);
    }

    @GetMapping("/view/{id}")
    public ResponseEntity<PostDto> getPost(
            @PathVariable Long id) {
        return postServices.fetchPost(id);
    }
    
    @GetMapping("/view-all")
    public ResponseEntity<Iterable<PostDto>> getAllPosts(){
        return new ResponseEntity<>(postServices.fetchPosts(), HttpStatus.OK);
    }

    @PutMapping("/{id}/{userId}/edit")
    public ResponseEntity<String> editPost(
            @PathVariable(value = "id") Long id,
            @PathVariable(value = "userId") Long userId,
            @RequestBody PostDto postDto
    ) {
        return postServices.editPost(postDto, id, userId);
    }

    @DeleteMapping("/{postId}/{userId}/delete")
    public ResponseEntity<String> deletePost(
            @PathVariable(value = "postId") Long postId,
            @PathVariable(value = "userId") Long userId){

        return postServices.deletePost(postId, userId);
    }


    @PostMapping("/{postId}/{userId}/like")
    public ResponseEntity<String> likePost(
            @PathVariable(value = "postId") Long postId,
            @PathVariable(value = "userId") Long userId)
    {
        postServices.likePost(postId, userId);
        return new ResponseEntity<>("Liked", HttpStatus.OK);
    }

    @DeleteMapping("/{postId}/{userId}/unlike")
    public ResponseEntity<String> unlikePost(
            @PathVariable(value = "postId") Long postId,
            @PathVariable(value = "userId") Long userId
    ) {
        postServices.likePost(postId, userId);
        return new ResponseEntity<>("Unliked", HttpStatus.OK);
    }

    @PostMapping("/{postId}/{userId}/comment")
    public ResponseEntity<String> makeCommentOnPost (
            @PathVariable(value = "postId") Long postId,
            @PathVariable(value = "userId") Long userId,
            @RequestBody CommentDto commentDto
            ) {
        postServices.commentOnAPost(commentDto,postId,userId);
        return new ResponseEntity<>("User comment saved.", HttpStatus.OK);
    }

    @DeleteMapping("/{postId}/{commentId}/{userId}/delete-comment")
    public ResponseEntity<String> deleteComment (
            @PathVariable(value = "postId") Long postId,
            @PathVariable(value = "commentId") Long commentId,
            @PathVariable(value = "userId") Long userId
    ) {
        postServices.deleteComment(postId, commentId, userId);
        return new ResponseEntity<>("Comment has been deleted!", HttpStatus.OK);
    }

//    @GetMapping("/view-likes/{postId}")
}
