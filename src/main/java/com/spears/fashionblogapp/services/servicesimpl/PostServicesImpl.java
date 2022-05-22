package com.spears.fashionblogapp.services.servicesimpl;

import com.spears.fashionblogapp.dto.CommentDto;
import com.spears.fashionblogapp.dto.PostDto;
import com.spears.fashionblogapp.entities.*;
import com.spears.fashionblogapp.exceptions.CategoryDoesNotExist;
import com.spears.fashionblogapp.exceptions.InvalidRequestException;
import com.spears.fashionblogapp.exceptions.PostDoesNotExistException;
import com.spears.fashionblogapp.exceptions.UserDoesNotExistException;
import com.spears.fashionblogapp.repositories.*;
import com.spears.fashionblogapp.services.PostServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostServicesImpl implements PostServices {

    private final PostRepository postRepo;
    private final LikesRepository likesRepo;
    private final BlogUserRepository blogUserRepo;
    private final CommentRepository commentRepo;
    private final CategoryRepository categoryRepo;
    private final ModelMapper mapper;

    @Autowired
    public PostServicesImpl(
            PostRepository postRepo,
            LikesRepository likesRepo,
            BlogUserRepository blogUserRepo,
            CommentRepository commentRepo,
            CategoryRepository categoryRepo,
            ModelMapper mapper
    ) {
        this.postRepo = postRepo;
        this.likesRepo = likesRepo;
        this.blogUserRepo = blogUserRepo;
        this.commentRepo = commentRepo;
        this.categoryRepo = categoryRepo;
        this.mapper = mapper;
    }


    @Override
    public ResponseEntity<String> createPost(PostDto postDto, Long userId, Long categoryId) {

        Post savedPost;

        BlogUser blogUser = blogUserRepo.findById(userId)
                .orElseThrow(()->new UserDoesNotExistException("There is no user with this ID."));

        if (!blogUser.getRole().equalsIgnoreCase("ADMIN")) {
            throw new InvalidRequestException("You are not authorised to create a post.");
        }

        savedPost = dtoToEntity(postDto,userId,categoryId);

        Post post = postRepo.save(savedPost);

        entityToDto(post);
        return new ResponseEntity<>("New Post has been Created", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> editPost(PostDto postDto, Long postId, Long userId) {

        Post editedPost = postRepo.findById(postId)
                .orElseThrow(()->new PostDoesNotExistException("There is no post with this ID"));

        BlogUser blogUser = blogUserRepo.findById(userId)
                .orElseThrow(()->new UserDoesNotExistException("There is no user with this ID"));

        if (!blogUser.getRole().equalsIgnoreCase("ADMIN")) {

            throw new InvalidRequestException("You are not authorised to perform this operation.");
        }
        editedPost.setId(postId);
        editedPost.setPostTitle(postDto.getTitle());
        editedPost.setPostContent(postDto.getContent());

        Post ep = postRepo.save(editedPost);

        entityToDto(ep);

        return new ResponseEntity<>("Post was updated", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<PostDto> fetchPost(Long postId) {
        Post post = postRepo.findById(postId)
                .orElseThrow(()-> new PostDoesNotExistException("There is no post with the ID"));
        return new ResponseEntity<>(mapper.map(post, PostDto.class),
                HttpStatus.OK);
    }

    @Override
    public List<PostDto> fetchPosts() {

        List<Post> posts = postRepo.findAll();
        List<PostDto> postDTOs = new ArrayList<>();
        for(Post po:posts){
            PostDto pd = entityToDto(po);
            postDTOs.add(pd);

        }
        return postDTOs;
    }

    @Override
    public ResponseEntity<String> deletePost(Long postId, Long userId) {

        BlogUser blogUser = blogUserRepo.findById(userId)
                .orElseThrow(()-> new UserDoesNotExistException("There is no user with this ID."));

        Post adminRemovePost = postRepo.findById(postId)
                .orElseThrow(()-> new PostDoesNotExistException("There is no post with this ID"));
        if (!blogUser.getRole().equalsIgnoreCase("ADMIN")) {

            throw new InvalidRequestException("You are not authorised to delete a post.");
        }
        postRepo.delete(adminRemovePost);
        return new ResponseEntity<>("Post has been deleted", HttpStatus.OK);
    }

    //  TODO Implement ModelMapper
    private PostDto entityToDto(Post post){
        return mapper.map(post, PostDto.class);
    }

    private Post dtoToEntity(PostDto postDto, Long userId, Long categoryId) {

        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(()-> new CategoryDoesNotExist("There is no category with this ID."));

        Post post = mapper.map(postDto, Post.class);
        post.setBlogUser(blogUserRepo.getById(userId));
        post.setPostDate(Date.valueOf(LocalDate.now()));
        post.setTime(Time.valueOf(LocalTime.now()));
        post.setCategory(category);

        return post;
    }


    @Override
    public void likePost(final Long postId, final Long userId) {
        Post post = postRepo.getById(postId);
        Likes likes = likesRepo.findLikesByPostIdAndBlogUserId(postId, userId);

        if (likes == null) {
            likes = new Likes();
            likes.setPost(postRepo.getById(postId));
            likes.setBlogUser(blogUserRepo.getById(userId));
            likesRepo.save(likes);
            post.setNumberOfLikes(post.getNumberOfLikes() + 1);

        } else {
            likesRepo.delete(likes);
        }
    }

    @Override
    public List<Likes> fetchLikesPerPost(Long postId) {

        return likesRepo.findAllByPost_Id(postId);
    }

    @Override
    public CommentDto commentOnAPost(final CommentDto commentDto, final Long postId, final Long userId) {
        Post post = postRepo.getById(postId);
        Comment comment = new Comment();
        comment.setPost(postRepo.getById(postId));
        comment.setBlogUser(blogUserRepo.getById(userId));
        comment.setCommentContent(commentDto.getCommentContent());
        comment.setCommentDate(Date.valueOf(LocalDate.now()));
        comment.setCommentTime(Time.valueOf(LocalTime.now()));
        commentRepo.save(comment);
        post.setNumberOfComments(post.getNumberOfComments() + 1);

        CommentDto commentDto1 = new CommentDto();
        commentDto1.setCommentId(comment.getCommentId());
        commentDto1.setCommentContent(comment.getCommentContent());

        return commentDto1;
    }

    @Override
    public void deleteComment(Long postId, Long commentId, Long userId) {

        BlogUser blogUser = blogUserRepo.findById(userId)
                .orElseThrow(()-> new UserDoesNotExistException("There is no user with this ID."));
        Post post = postRepo.findById(postId).
                orElseThrow(()-> new PostDoesNotExistException("There is no post with this ID"));

        Comment adminRemoveComment = commentRepo.findCommentByPostIdAndCommentId(postId, commentId);
        if (!blogUser.getRole().equalsIgnoreCase("ADMIN")) {

            throw new InvalidRequestException("You are not authorised to delete a comment.");
        }
        commentRepo.delete(adminRemoveComment);
    }
}
