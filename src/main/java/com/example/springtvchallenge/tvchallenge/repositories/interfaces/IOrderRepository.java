package com.example.springtvchallenge.tvchallenge.repositories.interfaces;

import com.example.springtvchallenge.tvchallenge.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IOrderRepository extends JpaRepository<Order, Long> { }
