package com.example.springtvchallenge.tvchallenge.controllers;

import com.example.springtvchallenge.tvchallenge.Dtos.OrderDto;
import com.example.springtvchallenge.tvchallenge.Dtos.OrderRequestDto;
import com.example.springtvchallenge.tvchallenge.entities.Order;
import com.example.springtvchallenge.tvchallenge.repositories.interfaces.IOrderRepository;
import com.example.springtvchallenge.tvchallenge.repositories.interfaces.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    IOrderRepository orderRepository;

    @Autowired
    IProductRepository productRepository;

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrder()
    {
        List<OrderDto> _orders = new ArrayList<>();

        orderRepository.findAll().forEach(order ->
        {
            _orders.add(new OrderDto(
                    order.getId(),
                    order.getName(),
                    order.getDescription(),
                    order.getProductsDto()
            ));
        });

        return new ResponseEntity<>(_orders, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable("id") long id)
    {
        Optional<Order> orderData = orderRepository.findById(id);

        if(!orderData.isPresent())
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        var _order = new OrderDto(
                orderData.get().getId(),
                orderData.get().getName(),
                orderData.get().getDescription(),
                orderData.get().getProductsDto());

        return new ResponseEntity<>(_order, HttpStatus.OK);
    }

    @PostMapping("/createOrder/{productId}")
    public ResponseEntity<OrderDto> createOrder(
            @PathVariable Long productId,
            @Validated @RequestBody Order orderRequest) throws ExecutionException, InterruptedException
    {
        var order = findOrderByProductId(productId, orderRequest);

        CompletableFuture.allOf(order).join();

        var orderData = new OrderDto(
                order.get().get().getId(),
                order.get().get().getName(),
                order.get().get().getDescription(),
                order.get().get().getProductsDto());

        return new ResponseEntity<>(orderData, HttpStatus.CREATED);
    }

    @PostMapping("/createOrder")
    public ResponseEntity<OrderDto> createOrder(
            @Validated @RequestBody OrderRequestDto orderRequest)
    {
        // Fail Fast Validation (if one of the products on the list does not exist)
        if(!allProductsExists(orderRequest.productIds))
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Order order = new Order(
                orderRequest.order.getName(),
                orderRequest.order.getDescription()
        );

        orderRequest.productIds.forEach(productId ->
        {
            var _product = productRepository.findById(productId);
            _product.get().setOrder(order);
        });

        orderRepository.save(order);

        var response = new OrderDto(
                order.getId(),
                order.getName(),
                order.getDescription(),
                order.getProductsDto());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDto> updateOrderById(
            @PathVariable long id,
            @Validated @RequestBody Order orderRequest)
    {
        var orderData = orderRepository.findById(id);

        if(!orderData.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        var order = orderData.get();
        order.setName(orderRequest.getName());
        order.setDescription(orderRequest.getDescription());

        OrderDto response = new OrderDto(
                orderData.get().getId(),
                orderData.get().getName(),
                orderData.get().getDescription(),
                orderData.get().getProductsDto());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteOrder(@PathVariable long id)
    {
        var orderData = orderRepository.findById(id);

        if(!orderData.isPresent())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        orderRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    protected boolean allProductsExists(List<Long> products)
    {
        for (Long productId : products) {
            var _product = productRepository.findById(productId);

            if(!_product.isPresent())
                return false;
        }
        return true;
    }

    @Async
    protected CompletableFuture<Optional<Order>> findOrderByProductId(
            long productId,
            Order orderRequest)
    {
        Optional<Order> order = productRepository.findById(productId).map(product ->
        {
            Order newOrder = new Order(
                    orderRequest.getName(),
                    orderRequest.getDescription()
            );

            product.setOrder(newOrder);

            return orderRepository.save(newOrder);
        });

        return CompletableFuture.completedFuture(order);
    }
}
