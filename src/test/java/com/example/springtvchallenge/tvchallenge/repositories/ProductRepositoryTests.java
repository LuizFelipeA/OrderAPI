package com.example.springtvchallenge.tvchallenge.repositories;

import com.example.springtvchallenge.tvchallenge.controllers.ProductController;
import com.example.springtvchallenge.tvchallenge.entities.Product;
import com.example.springtvchallenge.tvchallenge.repositories.interfaces.IProductRepository;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@WebMvcTest(controllers = ProductController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("ProductRepositoryTests")
public class ProductRepositoryTests
{
    @MockBean
    private IProductRepository productRepository;

    private List<Product> products;

    @BeforeAll
    void setup()
    {
        this.products = new ArrayList<>();

        this.products.add(new Product("Product name 1", "Product Description 1"));
        this.products.add(new Product("Product name 2", "Product Description 2"));
        this.products.add(new Product("Product name 3", "Product Description 3"));
    }

    @BeforeEach
    void init() { }

    @Test
    void shouldGetAllProductsSuccessfully()
    {
        // Action
        given(productRepository.findAll()).willReturn(products);

        List<Product> expectedProducts = productRepository.findAll();

        // Assert
        assertEquals(expectedProducts, products);
    }

    @Test
    void shouldGetProductByIdSuccessfully()
    {
        final Long id = 1L;
        final Product product = new Product("Product name", "Product Description");

        // Action
        given(productRepository.findById(id)).willReturn(Optional.of(product));

        final Optional<Product> expected = productRepository.findById(id);

        // Assert
        assertThat(expected).isNotNull();
    }

    @Test
    void shouldCreateProductSuccessfully()
    {
        final Product product = new Product("Product name", "Product Description");

        given(productRepository.findById(product.getId())).willReturn(Optional.empty());
        given(productRepository.save(product)).willAnswer(invocation -> invocation.getArgument(0));

        Product savedProduct = productRepository.save(product);

        assertThat(savedProduct).isNotNull();

        verify(productRepository).save(any(Product.class));
    }

    @Test
    void shouldUpdateProductSuccessfully()
    {
        final Long id = 1L;
        final Product product = new Product("Product name", "Product Description");
        String newProductName = "New product name";

        // Action
        given(productRepository.save(product)).willReturn(product);

        product.setName(newProductName);

        Product updatedProduct = productRepository.save(product);

        // Assert
        assertThat(product).isNotNull();

        assertEquals(updatedProduct.getName(), newProductName);

        verify(productRepository).save(any(Product.class));
    }

    @Test
    void shouldDeleteProductSuccessfully()
    {
        fail();
    }

    @Test
    void shouldNotCreateProductWithoutName()
    {
        fail();
    }

    @Test
    void shouldNotCreateProductWithoutDescription()
    {
        fail();
    }

    @Test
    void shouldNotUpdateProductWithoutName()
    {
        fail();
    }

    @Test
    void shouldNotUpdateProductWithoutDescription()
    {
        fail();
    }

    @AfterEach
    void tearDown() { }

    @AfterAll
    static void done() { }
}
