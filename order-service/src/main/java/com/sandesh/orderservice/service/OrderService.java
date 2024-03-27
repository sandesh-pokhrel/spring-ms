package com.sandesh.orderservice.service;

import com.sandesh.orderservice.model.Order;
import com.sandesh.orderservice.model.OrderItem;
import com.sandesh.orderservice.repository.OrderItemRepository;
import com.sandesh.orderservice.repository.OrderRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final EntityManager entityManager;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public void disassociate(long orderId, long orderItemId) {
        Optional<Order> orderOpt = orderRepository.findById(orderId);
//        orderOpt.ifPresent(order -> order.setTitle("Some random title"));
        Optional<OrderItem> orderItemOpt = orderItemRepository.findById(orderItemId);

        orderOpt.ifPresent(order -> {
            orderItemOpt.ifPresent(orderItem -> {
                order.getOrderItems().remove(orderItem);
            });
        });
    }
}
