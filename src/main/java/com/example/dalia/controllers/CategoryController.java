package com.example.dalia.controllers;
import com.example.dalia.DTOS.CategoryDTO;
import com.example.dalia.entities.Category;
import com.example.dalia.repositories.CategoryRepository;
import com.example.dalia.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;
    @PostMapping
    public String createCategory(@RequestBody Category category){
        categoryService.createCategory(category);
        return "category created successfully";
    }
    @GetMapping
    public List<CategoryDTO> getAllCategories(){
        return categoryService.getAllCategories();
    }
    @GetMapping("/{id}")
    public  Optional<CategoryDTO> getCategoryById(@PathVariable Long id){
        return categoryService.getCategoryById(id);
    }
    @DeleteMapping("/{id}")
    public String deleteCategoryById(@PathVariable long id){
        categoryService.deleteCategory(id);
        return "Category deleted successfully";
    }
    @PutMapping("/{id}")
    public String updateCategoryById(@PathVariable long id, @RequestBody Category category){
        return categoryService.updateCategory(id, category);
    }
}

