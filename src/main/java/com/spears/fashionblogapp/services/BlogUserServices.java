package com.spears.fashionblogapp.services;

import com.spears.fashionblogapp.dto.BlogUSerDto;
import org.springframework.http.ResponseEntity;

public interface BlogUserServices {
    ResponseEntity<String> createUser (BlogUSerDto blogUSerDto);

    ResponseEntity<String> createAdmin(BlogUSerDto blogUSerDto);
}
