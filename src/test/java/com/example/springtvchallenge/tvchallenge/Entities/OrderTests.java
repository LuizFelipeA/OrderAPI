package com.example.springtvchallenge.tvchallenge.Entities;

import com.example.springtvchallenge.tvchallenge.entities.Order;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Order entity test cases")
public class OrderTests
{
    @BeforeAll
    static void setup() { }

    @BeforeEach
    void init() { }

    @Test
    void failWhenNameIsNullOrEmpty()
    {
        String name = "";
        String description = "Product description";

        // Action
        var order = new Order(name, description);

        // Assert
        assertFalse(order.isValid());
    }

    @Test
    void failWhenDescriptionIsNullOrEmpty()
    {
        String name = "Product name";
        String description = "";

        // Action
        var order = new Order(name, description);

        // Assert
        assertFalse(order.isValid());
    }

    @Test
    void failWhenSettingNameAsNullOrEmpty()
    {
        String name = "Product name";
        String description = "Product description";

        // Action
        var order = new Order(name, description);

        order.setName("");

        // Assert
        assertFalse(order.isValid());
    }

    @Test
    void failWhenSettingDescriptionAsNullOrEmpty()
    {
        String name = "Product name";
        String description = "Product description";

        // Action
        var order = new Order(name, description);

        order.setDescription("");

        // Assert
        assertFalse(order.isValid());
    }
}
