package com.example.dalia.services;
import com.example.dalia.DTOS.ProductDTO;
import com.example.dalia.entities.Category;
import com.example.dalia.entities.Product;
import com.example.dalia.exceptions.CategoryNotFoundException;
import com.example.dalia.exceptions.ProductAlreadyExistException;
import com.example.dalia.exceptions.ProductNotFoundException;
import com.example.dalia.mappers.ProductMapper;
import com.example.dalia.repositories.CategoryRepository;
import com.example.dalia.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;
    
    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void CreateProduct_shouldThrowException_whenProductAlreadyExist(){
        Product product = new Product();
        when(productRepository.findByProductName(product.getProductName())).thenReturn(Optional.of(product));
        assertThrows(ProductAlreadyExistException.class, () -> productService.createProduct(product));
        verify(productRepository, never()).save(product);
    }

    @Test
    void createProduct_shouldThrowException_whenCategoryIdNotExist(){
        Product product = new Product();
        Category category = new Category();
        category.setId(1L);
        product.setCategory(category);
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(CategoryNotFoundException.class, () -> productService.createProduct(product));
    }

    @Test
    void createProduct_shouldSaveProduct() {
        Product product = new Product();
        Category category = new Category();
        product.setCategory(category);
        when(productRepository.findByProductName(product.getProductName())).thenReturn(Optional.empty());
        when(categoryRepository.findById(category.getId())).thenReturn(Optional.of(category));
        productService.createProduct(product);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void getProductById_shouldThrowException_whenProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.getProductById(1L));
    }

    @Test
    void GetProductById_shouldReturnProductDTO(){
        Product product = new Product();
        ProductDTO productDTO = new ProductDTO();
        when(productMapper.toProductDTO(product)).thenReturn(productDTO);
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        ProductDTO result = productService.getProductById(product.getId());
        assertNotNull(result);
        assertEquals(productDTO, result);
    }

    @Test
    void getAllProducts_shouldThrowException_whenNoProductsFound() {
        when(productRepository.findAll()).thenReturn(Collections.emptyList());
        assertThrows(ProductNotFoundException.class, () -> productService.getAllProducts());
    }

    @Test
    void getAllProducts_shouldReturnProductDTOs() {
        Product product = new Product();
        ProductDTO productDTO = new ProductDTO();
        when(productRepository.findAll()).thenReturn(List.of(product));
        when(productMapper.toProductDTO(product)).thenReturn(productDTO);
        List<ProductDTO> result = productService.getAllProducts();
        assertNotNull(result);
        assertTrue(result.size() > 0);
        assertEquals(productDTO, result.get(0));
    }

    @Test
    void findProductByProductName_shouldThrowException_whenProductNotFound() {
        when(productRepository.findByProductName("Nonexistent Product")).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.findProductByProductName("Nonexistent Product"));
    }

    @Test
    void findProductByProductName_shouldReturnProductDTO_whenProductFound() {
        Product product = new Product();
        ProductDTO productDTO = new ProductDTO();
        when(productRepository.findByProductName("Existing Product")).thenReturn(Optional.of(product));
        when(productMapper.toProductDTO(product)).thenReturn(productDTO);
        ProductDTO result = productService.findProductByProductName("Existing Product");
        assertNotNull(result);
        assertEquals(productDTO, result);
    }

    @Test
    void searchForProducts_shouldThrowException_whenNoProductsFound() {
        when(productRepository.findByProductNameContains("Nonexistent")).thenReturn(Collections.emptyList());
        assertThrows(ProductNotFoundException.class, () -> productService.searchForProducts("Nonexistent"));
    }

    @Test
    void searchForProducts_shouldReturnProductDTOs() {
        Product product = new Product();
        ProductDTO productDTO = new ProductDTO();
        when(productMapper.toProductDTO(product)).thenReturn(productDTO);
        when(productRepository.findByProductNameContains("Product")).thenReturn(List.of(product));
        List<ProductDTO> result = productService.searchForProducts("Product");
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(productDTO, result.get(0));
    }

    @Test
    void updateProduct_shouldThrowException_whenProductNotFound() {
        Product product = new Product();
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.updateProduct(1L, product));
    }

    @Test
    void updateProduct_shouldUpdateProduct() {
        Product existingProduct = new Product();
        existingProduct.setId(1L);
        Product updatedProduct = new Product();
        updatedProduct.setProductName("nouveau nom");
        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        productService.updateProduct(1L, updatedProduct);
        verify(productRepository, times(1)).save(existingProduct);
        assertEquals("nouveau nom", existingProduct.getProductName());
    }

    @Test
    void deleteProductById_shouldThrowException_whenProductNotFound() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ProductNotFoundException.class, () -> productService.deleteProductById(1L));
    }

    @Test
    void deleteProductById_shouldDeleteProduct_whenProductExists() {
        Product product = new Product();
        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        productService.deleteProductById(product.getId());
        verify(productRepository, times(1)).deleteById(product.getId());
    }
}
