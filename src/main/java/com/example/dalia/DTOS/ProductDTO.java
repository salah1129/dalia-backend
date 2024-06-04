package com.example.dalia.DTOS;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
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
    private String categoryName;
}
