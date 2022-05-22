package com.spears.fashionblogapp.repositories;



import com.spears.fashionblogapp.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

}
