package com.spears.fashionblogapp.repositories;



import com.spears.fashionblogapp.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findCommentByPostIdAndCommentId(Long postId, Long commentId);

}
