package com.example.dalia.services;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

import java.util.*;
import java.util.stream.Collectors;

import com.example.dalia.DTOS.CategoryDTO;
import com.example.dalia.entities.Category;
import com.example.dalia.exceptions.CategoryAlreadyExistException;
import com.example.dalia.exceptions.CategoryNotFoundException;
import com.example.dalia.mappers.CategoryMapper;
import com.example.dalia.repositories.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private CategoryMapper categoryMapper;

    @InjectMocks
    private CategoryService categoryService;

    @Test
    void getAllCategories_shouldReturnAllProducts() {
        // create categories
        Category category = new Category();
        CategoryDTO categoryDTO = new CategoryDTO();
        when(categoryMapper.toCategoryDTO(category)).thenReturn(categoryDTO);
        when(categoryRepository.findAll()).thenReturn(List.of(category));
        List<CategoryDTO> result = categoryService.getAllCategories();
        assertNotNull(result);
        assertTrue(result.size() > 0);
        assertEquals(result.get(0), categoryDTO);
    }

    @Test
    void getAllCategories_ShouldThrowException_WhenNoCategoryFound(){
        when(categoryRepository.findAll()).thenReturn(Collections.emptyList());
        assertThrows(CategoryNotFoundException.class, () -> categoryService.getAllCategories());
    }

    @Test
    void getCategoryById_ShouldReturnCategoryDTO(){
        Category category = new Category();
        CategoryDTO categoryDTO = new CategoryDTO();
        when(categoryMapper.toCategoryDTO(category)).thenReturn(categoryDTO);
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        Optional<CategoryDTO> result = categoryService.getCategoryById(category.getId());
        assertTrue(result.isPresent());
        assertEquals(categoryDTO, result.get());
    }

    @Test
    void getCategoryById_ShouldThrowException_WhenCategoryNotFound(){
        Long id = 1L;
        when(categoryRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(CategoryNotFoundException.class,() -> categoryService.getCategoryById(id));
    }

    @Test
    void CreateCategory_ShouldSaveCategory(){
        Category categoryToSave = new Category();
        when(categoryRepository.save(categoryToSave)).thenReturn(categoryToSave);
        categoryService.createCategory(categoryToSave);
        verify(categoryRepository).save(categoryToSave);
    }

    @Test
    void createCategory_ShouldThrowException_WhenCategoryNameAlreadyExist(){
        Category category = new Category();
        when(categoryRepository.findByCategoryName(category.getCategoryName())).thenReturn(Optional.of(category));
        assertThrows(CategoryAlreadyExistException.class, () -> categoryService.createCategory(category));
        verify(categoryRepository, never()).save(category);
    }

    @Test
    void updateCategoryById_ShouldThrowException_WhenCategoryNotFound(){
        Category categoryToUpdate = new Category();
        when(categoryRepository.findById(categoryToUpdate.getId())).thenReturn(Optional.empty());
        assertThrows(CategoryNotFoundException.class, () -> categoryService.updateCategory(categoryToUpdate.getId(), categoryToUpdate));
    }

    @Test
    void updateCategoryById_ShouldUpateCategory() {
        Category existingCategory = new Category();
        existingCategory.setId(1L);
        existingCategory.setCategoryName("Existing Category Name");
        Category newCategory = new Category();
        newCategory.setId(1L);
        newCategory.setCategoryName("New Category Name");
        when(categoryRepository.findById(existingCategory.getId())).thenReturn(Optional.of(existingCategory));
        String result = categoryService.updateCategory(existingCategory.getId(), newCategory);
        assertEquals("Category updated successfully", result);
        assertEquals("New Category Name", existingCategory.getCategoryName());
    }

    @Test
    void deleteCategoryById_ShouldDeleteCategory_WhenCategoryExists() {
        Category category = new Category();
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        categoryService.deleteCategory(category.getId());
        verify(categoryRepository, times(1)).deleteById(category.getId());
    }

    @Test
    void deleteCategoryById_ShouldThrowException_WhenCategoryNotExists() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(CategoryNotFoundException.class, () -> categoryService.deleteCategory(1L));
    }
}
