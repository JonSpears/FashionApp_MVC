package com.spears.fashionblogapp.services;

import com.spears.fashionblogapp.dto.CategoryDto;
import com.spears.fashionblogapp.dto.CommentDto;
import com.spears.fashionblogapp.dto.PostDto;
import com.spears.fashionblogapp.entities.Category;
import com.spears.fashionblogapp.entities.Likes;
import com.spears.fashionblogapp.entities.Post;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryServices {

    ResponseEntity<String> createCategory(CategoryDto categoryDto, Long userId);

    ResponseEntity<String> editCategory(CategoryDto categoryDto, Long categoryId, Long userId);

    ResponseEntity<CategoryDto> fetchCategory(Long categoryId);

    List<CategoryDto> fetchCategories();

    ResponseEntity<String> deleteCategory(Long CategoryId, Long userId);

}
