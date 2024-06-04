package com.example.dalia.mappers;
import com.example.dalia.DTOS.CategoryDTO;
import com.example.dalia.entities.Category;
import com.example.dalia.entities.Product;
import org.springframework.stereotype.Component;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {
    public CategoryDTO toCategoryDTO(Category category) {
        if (category == null)
            return null;
        return new CategoryDTO(
                category.getId(),
                category.getCategoryName(),
                category.getCategoryImage(),
                category.getBrands(),
                category.getCars(),
                category.getProducts().stream()
                        .map(Product::getProductName)
                        .collect(Collectors.toList())
        );
    }
}
