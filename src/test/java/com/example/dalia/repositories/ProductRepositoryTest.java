package com.example.dalia.repositories;

import com.example.dalia.entities.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private Product product;
    private Product productTwo;

    @BeforeEach
    void setUp(){
        productRepository.deleteAll();
        product = new Product();
        product.setId(1L);
        product.setProductName("exist");
        productTwo = new Product();
        productTwo.setProductName("product exist two");
        productRepository.save(product);
        productRepository.save(productTwo);
    }

    @Test
    public void testFindByProductName() {
        Optional<Product> foundProduct = productRepository.findByProductName("exist");
        assertThat(foundProduct).isPresent();
        assertThat(foundProduct.get().getProductName()).isEqualTo("exist");
    }

    @Test
    void testFindByProductNameContains() {
        List<Product> foundProducts = productRepository.findByProductNameContains("exist");
        assertThat(foundProducts).hasSize(2);
        assertThat(foundProducts).extracting(Product::getProductName)
                .containsExactlyInAnyOrder("exist", "product exist two");
    }

    @Test
    void testFindProductById() {
        Optional<Product> foundProduct = productRepository.findById(1L);
        assertThat(foundProduct).isPresent();
        assertThat(foundProduct.get().getProductName()).isEqualTo("exist");
    }

    @Test
    void testDeleteProductById() {
        productRepository.deleteById(1L);
        assertThat(productRepository.findById(1L)).isNotPresent();
    }

    @Test
    void testSaveProduct() {
        Product newProduct = new Product();
        newProduct.setProductName("new product");
        productRepository.save(newProduct);
        assertThat(productRepository.findById(newProduct.getId())).isPresent();
        assertThat(productRepository.findById(newProduct.getId()).get().getProductName()).isEqualTo("new product");
    }
}
