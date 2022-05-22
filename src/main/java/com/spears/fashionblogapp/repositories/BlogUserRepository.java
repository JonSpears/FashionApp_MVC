package com.spears.fashionblogapp.repositories;

import com.spears.fashionblogapp.entities.BlogUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogUserRepository extends JpaRepository<BlogUser, Long> {
    BlogUser findById(long id);
    BlogUser findByUsername(String username);
}
