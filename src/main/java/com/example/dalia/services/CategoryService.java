package com.example.dalia.services;

import com.example.dalia.DTOS.CategoryDTO;
import com.example.dalia.entities.Category;
import com.example.dalia.exceptions.CategoryAlreadyExistException;
import com.example.dalia.exceptions.CategoryNotFoundException;
import com.example.dalia.repositories.CategoryRepository;
import com.example.dalia.mappers.CategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, CategoryMapper categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new CategoryNotFoundException("No categories found");
        } else {
            return categories.stream()
                    .map(categoryMapper::toCategoryDTO)
                    .collect(Collectors.toList());
        }
    }

    public Optional<CategoryDTO> getCategoryById(long id){
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if(optionalCategory.isEmpty()){
            throw  new CategoryNotFoundException("category with id " + id +" not found" );
        }
        return optionalCategory.map(categoryMapper::toCategoryDTO);
    }

    public void createCategory(Category category) {
        Optional<Category> existingCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if(existingCategory.isPresent()){
            throw new CategoryAlreadyExistException("Category with name " + category.getCategoryName() + " already exist");

        } else {
            categoryRepository.save(category);
        }
    }

    public String updateCategory(long id, Category newCategory) {
        Optional<Category> existingCategory = categoryRepository.findById(id);
        if (existingCategory.isEmpty()) {
            throw new CategoryNotFoundException("Category with id " + id + " not found");
        } else {
            Category categoryToUpdate = existingCategory.get();
            categoryToUpdate.setCategoryName(newCategory.getCategoryName());
            categoryRepository.save(categoryToUpdate);
            return "Category updated successfully";
        }
    }

    public void deleteCategory(long id) {
        Optional<Category> existingCategory = categoryRepository.findById(id);
        if(existingCategory.isEmpty()){
            throw  new CategoryNotFoundException("category with id " + id +" not found" );
        }
        else  {
            categoryRepository.deleteById(id);
        }
    }


}
