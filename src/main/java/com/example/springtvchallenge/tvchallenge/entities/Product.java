package com.example.springtvchallenge.tvchallenge.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "products")
public class Product
{
    public Product() { }

    public Product(
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

    @ManyToMany(mappedBy = "products")
    private List<Order> orders;

    @JsonIgnore
    private boolean isValid = true;

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

    public void setOrder(Order order)
    {
        if(order == null)
            this.isValid = false;
        else
        {
            this.orders.add(order);
            order.getProducts().add(this);
        }
    }

    public boolean isValid()
    {
        return isValid;
    }
}
