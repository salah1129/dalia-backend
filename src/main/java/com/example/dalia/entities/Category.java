package com.example.dalia.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String categoryName;
    private String categoryImage;
    private String categoryDescription;

    @Transient
    private Set<String> brands = new HashSet<>();

    @Transient 
    private Set<String> cars = new HashSet<>();

    @OneToMany(mappedBy = "category")
    private List<Product> products;

    @PostLoad
    private void populateBrandsAndCars() {
        for (Product product : products) {
            brands.add(product.getBrand());
            cars.add(product.getTypeOfCar());
        }
    }
}
