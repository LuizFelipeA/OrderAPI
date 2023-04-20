package com.example.springtvchallenge.tvchallenge.repositories.interfaces;

import com.example.springtvchallenge.tvchallenge.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IProductRepository extends JpaRepository<Product, Long> { }
