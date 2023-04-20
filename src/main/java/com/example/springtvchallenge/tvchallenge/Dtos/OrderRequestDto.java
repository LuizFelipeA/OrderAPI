package com.example.springtvchallenge.tvchallenge.Dtos;

import com.example.springtvchallenge.tvchallenge.entities.Order;

import java.util.List;

public class OrderRequestDto
{
    public Order order;

    public List<Long> productIds;
}
