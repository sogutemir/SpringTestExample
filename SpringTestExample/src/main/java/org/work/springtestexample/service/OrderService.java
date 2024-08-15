package org.work.springtestexample.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.work.springtestexample.dto.OrderDTO;
import org.work.springtestexample.model.Order;
import org.work.springtestexample.repository.OrderRepository;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            return null;
        }

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setUserId(order.getUserId());
        orderDTO.setProductId(order.getProductId());
        orderDTO.setQuantity(order.getQuantity());

        return orderDTO;
    }

    public OrderDTO saveOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setUserId(orderDTO.getUserId());
        order.setProductId(orderDTO.getProductId());
        order.setQuantity(orderDTO.getQuantity());

        order = orderRepository.save(order);
        orderDTO.setId(order.getId());
        return orderDTO;
    }
}