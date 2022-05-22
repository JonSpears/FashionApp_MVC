package com.spears.fashionblogapp.controllers;

import com.spears.fashionblogapp.dto.CategoryDto;
import com.spears.fashionblogapp.services.CategoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryServices categoryServices;

    @Autowired
    public CategoryController(CategoryServices categoryServices) {
        this.categoryServices = categoryServices;
    }

    @PostMapping("/{userId}/create")
    public ResponseEntity<String> createCategory (
            @PathVariable(value = "userId") Long userId,
            @RequestBody CategoryDto categoryDto
            ) {
        return categoryServices .createCategory(categoryDto,userId);
    }

    @PutMapping("/{categoryId}/{userId}/edit")
    public ResponseEntity<String> editCategory (
            @PathVariable(value = "categoryId") Long categoryId,
            @PathVariable(value = "userId") Long userId,
            @RequestBody CategoryDto categoryDto
    ) {
        return categoryServices.editCategory(categoryDto, categoryId, userId);
    }

    @GetMapping("/{categoryId}/view")
    public ResponseEntity<CategoryDto> viewCategoryById (
            @PathVariable(value = "categoryId") Long categoryId) {
        return categoryServices.fetchCategory(categoryId);
    }

    @GetMapping("/view-all")
    public ResponseEntity<Iterable<CategoryDto>> viewAllCategories (){
        return new ResponseEntity<>(categoryServices.fetchCategories(), HttpStatus.OK);
    }

}
