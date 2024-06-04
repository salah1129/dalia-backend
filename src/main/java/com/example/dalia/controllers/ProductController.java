package com.example.dalia.controllers;
import com.example.dalia.DTOS.ProductDTO;
import com.example.dalia.entities.Category;
import com.example.dalia.entities.Product;
import com.example.dalia.repositories.ProductRepository;
import com.example.dalia.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping
public class ProductController {
    @Autowired
    private ProductService productService;

    @PostMapping("/products")
    public String createProduct(@RequestBody Product product){
        productService.createProduct(product);
        return "Product created successfully";
    }

    @GetMapping("/products")
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping(path = "/products/{id}")
    public ProductDTO getProductById(@PathVariable long id){

        return productService.getProductById(id);
    }

    @GetMapping(path = "/findProductByName/{productName}")
    public ProductDTO getProductByProductName(@PathVariable String productName){
        return productService.findProductByProductName(productName);
    }

    @GetMapping(path = "/searchForProducts/{productName}")
    public List<ProductDTO> searchForProduct(@PathVariable String productName){
        return productService.searchForProducts(productName);
    }

    @DeleteMapping("/products/{id}")
    public void deleteProductById(@PathVariable long id, @RequestBody Product product){
        productService.deleteProductById(id);
    }
    @PutMapping("/products/{id}")
    public void updateProductById(@PathVariable long id, @RequestBody Product product){
        productService.updateProduct(id, product);
    }
}
