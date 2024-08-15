package org.work.springtestexample.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.work.springtestexample.dto.OrderDTO;
import org.work.springtestexample.service.OrderService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        OrderDTO orderDTO = orderService.getOrderById(id);
        if (orderDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderDTO);
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO orderDTO) {
        OrderDTO savedOrder = orderService.saveOrder(orderDTO);
        return ResponseEntity.ok(savedOrder);
    }
}