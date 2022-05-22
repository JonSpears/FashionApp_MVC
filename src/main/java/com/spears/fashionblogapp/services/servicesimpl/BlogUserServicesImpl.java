package com.spears.fashionblogapp.services.servicesimpl;

import com.spears.fashionblogapp.dto.BlogUSerDto;
import com.spears.fashionblogapp.entities.BlogUser;
import com.spears.fashionblogapp.repositories.BlogUserRepository;
import com.spears.fashionblogapp.services.BlogUserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


@Service
public class BlogUserServicesImpl implements BlogUserServices {

    @Autowired
    private BlogUserRepository blogUserRepo;

    @Override
    public ResponseEntity<String> createUser(BlogUSerDto blogUSerDto) {

        BlogUser createdUser = new BlogUser();
        createdUser.setUsername(blogUSerDto.getUsername());
        createdUser.setEmail(blogUSerDto.getEmail());
        createdUser.setPassword(blogUSerDto.getPassword());
        createdUser.setName(blogUSerDto.getName());
        createdUser.setRole("USER");
        BlogUser blogUser = blogUserRepo.save(createdUser);

        BlogUSerDto blogUSerDto1 = new BlogUSerDto();
        blogUSerDto1.setId(blogUser.getUserId());
        blogUSerDto1.setUsername(blogUser.getUsername());
        blogUSerDto1.setEmail(blogUser.getEmail());

        return new ResponseEntity<>("New User Created!", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<String> createAdmin(BlogUSerDto blogUSerDto) {

        BlogUser createdAdmin = new BlogUser();
        createdAdmin.setUsername(blogUSerDto.getUsername());
        createdAdmin.setEmail(blogUSerDto.getEmail());
        createdAdmin.setPassword(blogUSerDto.getPassword());
        createdAdmin.setName(blogUSerDto.getName());
        createdAdmin.setRole("ADMIN");
        BlogUser blogUser = blogUserRepo.save(createdAdmin);

        BlogUSerDto blogUSerDto2 = new BlogUSerDto();
        blogUSerDto2.setId(blogUser.getUserId());
        blogUSerDto2.setUsername(blogUser.getUsername());
        blogUSerDto2.setEmail(blogUser.getEmail());

        return new ResponseEntity<>("Admin Created!", HttpStatus.CREATED);
    }
}
