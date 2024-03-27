package com.sandesh.orderservice.controller;

import com.sandesh.orderservice.model.Order;
import com.sandesh.orderservice.model.OrderItem;
import com.sandesh.orderservice.repository.OrderItemRepository;
import com.sandesh.orderservice.repository.OrderRepository;
import com.sandesh.orderservice.service.OrderService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;

    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @GetMapping("/no-response")
    @ResponseStatus(HttpStatus.OK)
    public List<Order> getAllOrdersNoResponse() {
         return orderRepository.findAllOrdersWithOrderItems();
    }

    @GetMapping("/custom/{title}")
    public Order getOrderByTitle(@PathVariable String title) {
        return orderRepository.findByTitleContainsCustom(title);
    }

    @GetMapping("/{id}")
    public Order getOrderById(@PathVariable long id) {
        return orderRepository.findById(id).orElse(null);
    }


    @DeleteMapping("/disassociate/{orderId}/{orderItemId}")
    public void disassociate(@PathVariable long orderId, @PathVariable long orderItemId) {
        orderService.disassociate(orderId, orderItemId);
    }
}
