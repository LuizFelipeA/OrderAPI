package com.example.springtvchallenge.tvchallenge.controllers;

import com.example.springtvchallenge.tvchallenge.Dtos.ProductDto;
import com.example.springtvchallenge.tvchallenge.entities.Product;
import com.example.springtvchallenge.tvchallenge.repositories.interfaces.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
public class ProductController
{
    @Autowired
    IProductRepository productRepository;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts()
    {
        List<ProductDto> products = new ArrayList<>();

        productRepository.findAll().forEach(product ->
        {
           products.add(new ProductDto(
                   product.getId(),
                   product.getName(),
                   product.getDescription()
           ));
        });

        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable("id") long id)
    {
        Optional<Product> productData = productRepository.findById(id);

        if(!productData.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Product product = productData.get();

        var response = new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(
            @Validated @RequestBody Product productRequest)
    {
        Product product = productRepository.save(new Product(
                productRequest.getName(),
                productRequest.getDescription()));

        var response = new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProductById(
            @PathVariable("id") long id,
            @Validated @RequestBody Product productRequest)
    {
        Optional<Product> productData = productRepository.findById(id);

        if(!productData.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Product product = productData.get();

        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());

        productRepository.save(product);

        var response = new ProductDto(
                product.getId(),
                product.getName(),
                product.getDescription());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteProduct(@PathVariable("id") long id)
    {
        Optional<Product> productData = productRepository.findById(id);

        if(!productData.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        productRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
