package com.example.dalia.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDTO {
    private long id;
    private String categoryName;
    private String image;
    private Set<String> brands ;
    private Set<String> cars;
    private List<String> products;
}
