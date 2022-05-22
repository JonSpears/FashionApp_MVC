package com.spears.fashionblogapp.repositories;



import com.spears.fashionblogapp.entities.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikesRepository extends JpaRepository<Likes, Long> {



    @Query(
            value = "select * from likes where post_id = ?1 and user_id = ?2",
            nativeQuery = true
    )
    Likes findLikesByPostIdAndBlogUserId (Long postId, Long userId);

    List<Likes> findAllByPost_Id (Long postId);

}
