package com.example.springtvchallenge.tvchallenge.Dtos;

public class ProductDto
{
    public ProductDto(
            long id,
            String name,
            String description) {
        this.id = id;
        this.name = name;
        Description = description;
    }

    public long id;

    public String name;

    public String Description;
}
