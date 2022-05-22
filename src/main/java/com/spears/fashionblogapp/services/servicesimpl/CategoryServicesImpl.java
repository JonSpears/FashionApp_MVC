package com.spears.fashionblogapp.services.servicesimpl;

import com.spears.fashionblogapp.dto.CategoryDto;
import com.spears.fashionblogapp.entities.BlogUser;
import com.spears.fashionblogapp.entities.Category;
import com.spears.fashionblogapp.exceptions.CategoryDoesNotExist;
import com.spears.fashionblogapp.exceptions.InvalidRequestException;
import com.spears.fashionblogapp.exceptions.UserDoesNotExistException;
import com.spears.fashionblogapp.repositories.BlogUserRepository;
import com.spears.fashionblogapp.repositories.CategoryRepository;
import com.spears.fashionblogapp.repositories.PostRepository;
import com.spears.fashionblogapp.services.CategoryServices;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServicesImpl implements CategoryServices {

    private final CategoryRepository categoryRepo;
    private final PostRepository postRepo;
    private final BlogUserRepository blogUserRepo;
    private final ModelMapper mapper;

    @Autowired
    public CategoryServicesImpl(
            CategoryRepository categoryRepo,
            PostRepository postRepo,
            BlogUserRepository blogUserRepo,
            ModelMapper mapper
    ) {
        this.categoryRepo = categoryRepo;
        this.postRepo = postRepo;
        this.blogUserRepo = blogUserRepo;
        this.mapper = mapper;
    }

    @Override
    public ResponseEntity <String> createCategory(CategoryDto categoryDto, Long userId) {

        Category postCategory;
        BlogUser blogUser = blogUserRepo.findById(userId)
                .orElseThrow(()-> new UserDoesNotExistException("There is no user with this ID."));

        if (!blogUser.getRole().equalsIgnoreCase("ADMIN")){
            throw new InvalidRequestException("You are not authorised to perform this operation");
        }
        postCategory = categoryDtoToEntity(categoryDto, userId);
        Category category = categoryRepo.save(postCategory);

        categoryEntityToDto(category);

        return new ResponseEntity<>("A new category has been created", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> editCategory(CategoryDto categoryDto, Long categoryId, Long userId) {
        Category editedCategory = new Category();
        BlogUser blogUser = blogUserRepo.findById(userId)
                .orElseThrow(()-> new UserDoesNotExistException("There is no user with this ID."));
        if (!blogUser.getRole().equalsIgnoreCase("ADMIN"))
            throw new InvalidRequestException("You are not authorised to perform this operation.");
        editedCategory.setCategoryName(categoryDto.getCategoryName());
        editedCategory.setCategoryDescription(categoryDto.getCategoryDescription());
        Category categoryEdit = categoryRepo.save(editedCategory);

        categoryEntityToDto(categoryEdit);

        return new ResponseEntity<>("You have updated this category", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<CategoryDto> fetchCategory(Long categoryId) {
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(()-> new CategoryDoesNotExist("There is no category with this ID."));
        return new ResponseEntity<>(mapper.map(category, CategoryDto.class), HttpStatus.OK);
    }

    @Override
    public List<CategoryDto> fetchCategories() {
        List<Category> categories = categoryRepo.findAll();
        List<CategoryDto> categoryDTOs = new ArrayList<>();
        for (Category category : categories) {
            CategoryDto cDto = categoryEntityToDto(category);
            categoryDTOs.add(cDto);
        }
        if (categoryDTOs.isEmpty()) {
            throw new CategoryDoesNotExist("There are no categories.");
        }
        return categoryDTOs;
    }

    @Override
    public ResponseEntity<String> deleteCategory(Long categoryId, Long userId) {
        BlogUser blogUser = blogUserRepo.findById(userId)
                .orElseThrow(()-> new UserDoesNotExistException("There is no user with this ID."));

        Category adminDeleteCategory = categoryRepo.findById(categoryId)
                .orElseThrow(()-> new CategoryDoesNotExist("There is no category with this ID."));

        if (!blogUser.getRole().equalsIgnoreCase("ADMIN"))
            throw new InvalidRequestException("You are not authorised to carry out this operation.");

        categoryRepo.delete(adminDeleteCategory);

        return new ResponseEntity<>("Category has been deleted!", HttpStatus.OK);
    }

    //  Todo Category Model Converter
    private CategoryDto categoryEntityToDto (Category category){
        return mapper.map(category, CategoryDto.class);
    }

    private Category categoryDtoToEntity (CategoryDto categoryDto, Long userId){
        Category category = mapper.map(categoryDto, Category.class);
        category.setBlogUser(blogUserRepo.getById(userId));
        return category;
    }

}
