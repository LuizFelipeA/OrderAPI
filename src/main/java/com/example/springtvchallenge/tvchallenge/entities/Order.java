package com.example.springtvchallenge.tvchallenge.entities;

import com.example.springtvchallenge.tvchallenge.Dtos.ProductDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order
{
    public Order() { }

    public Order(
            String name,
            String description)
    {
        if(name.isEmpty() || name == null)
            this.isValid = false;

        if(description.isEmpty() || description == null)
            this.isValid = false;

        if(this.isValid)
        {
            this.name = name;
            this.description = description;
        }
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @JsonIgnore
    private boolean isValid = true;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "order_items",
            joinColumns = @JoinColumn(name = "order_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "product_id", referencedColumnName = "id"))
    private List<Product> products = new ArrayList<>();

    public long getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        if(name == null || name.isEmpty())
            this.isValid = false;
        else
            this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        if(description == null || description.isEmpty())
            this.isValid = false;
        else
            this.description = description;
    }

    public List<ProductDto> getProductsDto()
    {
        List<ProductDto> productsList = new ArrayList<>();

        products.forEach(product ->
        {
            productsList.add(new ProductDto(
                    product.getId(),
                    product.getName(),
                    product.getDescription()));
        });

        return productsList;
    }

    public List<Product> getProducts()
    {
        return products;
    }

    public boolean isValid()
    {
        return this.isValid;
    }
}
