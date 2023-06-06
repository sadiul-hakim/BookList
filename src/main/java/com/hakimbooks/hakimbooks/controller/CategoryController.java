package com.hakimbooks.hakimbooks.controller;

import com.hakimbooks.hakimbooks.model.Category;
import com.hakimbooks.hakimbooks.pojo.ApiResponse;
import com.hakimbooks.hakimbooks.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/category")
public class CategoryController {
    private final CategoryService categoryService;
    @PostMapping("/post")
    public ResponseEntity<Category> createCategory(@RequestBody Category category){
        Category savedCategory = categoryService.create(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedCategory);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategory(@PathVariable long categoryId){
        Category category = categoryService.getCategory(categoryId);
        return ResponseEntity.ok(category);
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<Category> getCategoryByField(@PathVariable String category){
        Category category1 = categoryService.getCategoryByField(category);
        return ResponseEntity.ok(category1);
    }

    @GetMapping("/list")
    public ResponseEntity<List<Category>> getCategoryList(){
        List<Category> categoryList = categoryService.getAll();
        return ResponseEntity.ok(categoryList);
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable long categoryId){
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.ok(new ApiResponse("Category deleted successfully.",true));
    }
}
