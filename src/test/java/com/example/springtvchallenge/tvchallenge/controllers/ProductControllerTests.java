package com.example.springtvchallenge.tvchallenge.controllers;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;

@WebMvcTest(controllers = ProductController.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("ActiveProfileTest")
public class ProductControllerTests
{
    @BeforeAll
    void setup() { }

    @BeforeEach
    void init() { }

    @AfterEach
    void tearDown() { }

    @AfterAll
    static void done() { }
}
