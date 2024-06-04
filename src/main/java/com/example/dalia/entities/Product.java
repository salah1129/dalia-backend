package com.example.dalia.entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String productName;
    private String productImage;
    private List<String> images;
    private String productDescription;
    private Long price;
    private String Ref;
    private String howToUse;
    private String moreInfo;
    private int remainingPieces;
    private String brand;
    private  String typeOfCar;
    @ManyToOne
    private Category category;
}

