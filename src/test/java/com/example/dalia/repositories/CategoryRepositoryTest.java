package com.example.dalia.repositories;

import com.example.dalia.entities.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class CategoryRepositoryTest {
    @Autowired
    private  CategoryRepository categoryRepository;
    private Category category;

    @BeforeEach
    void setUp(){
        categoryRepository.deleteAll();
        category = new Category();
        category.setCategoryName("exist");
        category.setId(1L);
        categoryRepository.save(category);
    }

    @Test
    void testFindByCategoryName(){
        String categoryNameToSearch = "exist";
        Optional<Category> category = categoryRepository.findByCategoryName(categoryNameToSearch);
        assertTrue(category.isPresent());
    }

    @Test
    void testFindCategoryByID() {
        Optional<Category> foundCategory = categoryRepository.findById(1L);
        assertTrue(foundCategory.isPresent());
    }

    @Test
    void itShouldSaveCategory(){
        Category c = new Category();
        Category category = categoryRepository.save(c);
        assertTrue(categoryRepository.findById(category.getId()).isPresent());
    }

    @Test
    void testDeleteCategoryById(){
        categoryRepository.deleteById(1L);
        assertTrue(categoryRepository.findById(1L).isEmpty());
    }
}