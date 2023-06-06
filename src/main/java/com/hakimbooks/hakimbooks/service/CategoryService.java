package com.hakimbooks.hakimbooks.service;

import com.hakimbooks.hakimbooks.exception.ResourceNotFoundException;
import com.hakimbooks.hakimbooks.model.Category;
import com.hakimbooks.hakimbooks.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category create(Category category){
        return categoryRepository.save(category);
    }

    public Category getCategory(long category){
        return categoryRepository.findById(category)
                .orElseThrow(()->new ResourceNotFoundException("Category not found with id: "+category));
    }

    public Category getCategoryByField(String category){
        return categoryRepository.findByCategory(category)
                .orElseThrow(()->new ResourceNotFoundException("Category not found with category: "+category));
    }

    public List<Category> getAll(){
        return categoryRepository.findAll();
    }

    public void deleteCategory(long categoryId){
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));
        categoryRepository.delete(category);
    }
}
