package com.example.springtvchallenge.tvchallenge.Dtos;

import com.example.springtvchallenge.tvchallenge.entities.Product;

import java.util.List;

public class OrderDto
{
    public OrderDto(
            long id,
            String name,
            String description,
            List<ProductDto> products) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.products = products;
    }

    public long id;

    public String name;

    public String description;

    public List<ProductDto> products;
}
