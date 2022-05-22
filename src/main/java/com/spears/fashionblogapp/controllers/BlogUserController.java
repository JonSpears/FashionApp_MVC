package com.spears.fashionblogapp.controllers;

import com.spears.fashionblogapp.dto.BlogUSerDto;
import com.spears.fashionblogapp.services.BlogUserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class BlogUserController {

    private final BlogUserServices blogUserServices;

    @Autowired
    public BlogUserController(BlogUserServices blogUserServices) {
        this.blogUserServices = blogUserServices;
    }

    @PostMapping("/create-user")
    public ResponseEntity<String> createUser(@RequestBody BlogUSerDto blogUSerDto){
        return blogUserServices.createUser(blogUSerDto);
    }

    @PostMapping("/create-admin")
    public ResponseEntity<String> createUserAdmin(@RequestBody BlogUSerDto blogUSerDto){
        return blogUserServices.createAdmin(blogUSerDto);
    }
}
