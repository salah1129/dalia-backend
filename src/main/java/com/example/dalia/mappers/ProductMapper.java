package com.example.dalia.mappers;

import com.example.dalia.DTOS.ProductDTO;
import com.example.dalia.entities.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {
    public ProductDTO toProductDTO(Product product) {
        if (product == null) {
            return null;
        }
        return new ProductDTO(
                product.getId(),
                product.getProductName(),
                product.getProductImage(),
                product.getImages(),
                product.getProductDescription(),
                product.getPrice(),
                product.getRef(),
                product.getHowToUse(),
                product.getMoreInfo(),
                product.getRemainingPieces(),
                product.getBrand(),
                product.getTypeOfCar(),
                product.getCategory() != null ? product.getCategory().getCategoryName() : null
        );
    }

}
