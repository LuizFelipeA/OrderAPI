package com.example.springtvchallenge.tvchallenge.Entities;

import com.example.springtvchallenge.tvchallenge.entities.Order;
import com.example.springtvchallenge.tvchallenge.entities.Product;
import org.junit.jupiter.api.*;


import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Product entity test cases")
public class ProductTests
{

    @BeforeAll
    static void setup() { }

    @BeforeEach
    void init() { }

    @Test
    void failWhenNameIsNullOrEmpty()
    {
        String name = "";
        String description = "product description";

        // Action
        var product = new Product(name, description);

        // Assert
        assertFalse(product.isValid());
    }

    @Test
    void failWhenDescriptionIsNullOrEmpty()
    {
        String name = "product name";
        String description = "";

        // Action
        var product = new Product(name, description);

        // Assert
        assertFalse(product.isValid());
    }

    @Test
    void failWhenSettingNullOrEmptyDescription()
    {
        String name = "Product name";
        String description = "Product description";

        // Action
        var product = new Product(name, description);

        product.setDescription("");

        // Assert
        assertFalse(product.isValid());
    }

    @Test
    void failWhenSettingNullOrEmptyName()
    {
        String name = "Product name";
        String description = "Product description";

        // Action
        var product = new Product(name, description);

        product.setName("");

        // Assert
        assertFalse(product.isValid());
    }

    @Test
    void failWhenSettingNullOrEmptyListOfOrder()
    {
        String name = "Product name";
        String description = "Product description";
        Order order = null;

        // Action
        var product = new Product(name, description);

        product.setOrder(order);

        // Assert
        assertFalse(product.isValid());
    }

    @AfterEach
    void tearDown() { }

    @AfterAll
    static void done() { }
}
