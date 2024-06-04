package com.example.dalia.services;
import com.example.dalia.DTOS.ProductDTO;
import com.example.dalia.entities.Category;
import com.example.dalia.exceptions.CategoryNotFoundException;
import com.example.dalia.exceptions.ProductAlreadyExistException;
import com.example.dalia.exceptions.ProductNotFoundException;
import com.example.dalia.repositories.CategoryRepository;
import com.example.dalia.repositories.ProductRepository;
import com.example.dalia.entities.Product;
import com.example.dalia.mappers.ProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;
    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.productMapper = productMapper;
    }
    public void createProduct(Product product){
        boolean existingProduct = productRepository.findByProductName(product.getProductName()).isPresent();
        if(existingProduct){
            throw new ProductAlreadyExistException("Product with name " + product.getProductName() + " already exist");
        }
        boolean existingCategory = categoryRepository.findById(product.getCategory().getId()).isPresent();
        if(!existingCategory){
            throw new CategoryNotFoundException("Category with ID " + product.getCategory().getId() + " not found");
        }
        productRepository.save(product);
    }
    public ProductDTO getProductById(long id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isEmpty()){
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }
        return optionalProduct.map(productMapper::toProductDTO).orElse(null);
    }
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()){
            throw new ProductNotFoundException("No product found");
        } else {
            return products.stream()
                    .map(productMapper::toProductDTO)
                    .collect(Collectors.toList());
        }
    }
    public ProductDTO findProductByProductName(String productName){
        Optional<Product> optionalProduct = productRepository.findByProductName(productName);
        if(optionalProduct.isEmpty()){
            throw new ProductNotFoundException("Product with name " + productName + " not found");
        }
        return optionalProduct.map(productMapper::toProductDTO).orElse(null);
    }
    public List<ProductDTO> searchForProducts(String productName) {
        List<Product> products = productRepository.findByProductNameContains(productName);
        if(products.isEmpty()){
            throw new ProductNotFoundException("No product found");
        }
        return products.stream()
                .map(productMapper::toProductDTO)
                .collect(Collectors.toList());
    }
    public void updateProduct(long id, Product product) {
        Optional<Product> existingProduct = productRepository.findById(id);
        if(existingProduct.isEmpty()){
            throw new ProductNotFoundException("Product with id " + id + " not found");
        } else {
            Product existing = existingProduct.get();
            if (product.getProductName() != null) {
                existing.setProductName(product.getProductName());
            }
            if (product.getProductImage() != null) {
                existing.setProductImage(product.getProductImage());
            }
            if (product.getProductDescription() != null) {
                existing.setProductDescription(product.getProductDescription());
            }
            if (product.getPrice() != null) {
                existing.setPrice(product.getPrice());
            }
            if (product.getBrand() != null) {
                existing.setBrand(product.getBrand());
            }
            if (product.getRef() != null) {
                existing.setRef(product.getRef());
            }
            if (product.getHowToUse() != null) {
                existing.setHowToUse(product.getHowToUse());
            }
            if (product.getMoreInfo() != null) {
                existing.setMoreInfo(product.getMoreInfo());
            }
            if (product.getRemainingPieces() != 0) {
                existing.setRemainingPieces(product.getRemainingPieces());
            }
            if (product.getCategory() != null) {
                existing.setCategory(product.getCategory());
            }
            productRepository.save(existing);
        }
    }
    public void deleteProductById(Long id) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));

        productRepository.deleteById(id);
    }
}
