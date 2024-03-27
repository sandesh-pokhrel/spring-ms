package com.sandesh.orderservice.repository;

import com.sandesh.orderservice.model.Order;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    @Transactional
    @Query("select o from Order o join fetch o.orderItems")
    List<Order> findAllOrdersWithOrderItems();

    @Transactional
    @EntityGraph(value = "Order.OrderItems")
    Order findByTitleContains(String title);

    @Transactional
    @EntityGraph(value = "Order.OrderItems")
    @Query("select o from Order o where o.title like %?1%")
    Order findByTitleContainsCustom(String title);

    @EntityGraph(value = "Order.OrderItems")
    Optional<Order> findById(long id);

    @Transactional
    @Query("select o from Order o")
    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = "orderItems")
    List<Order> findAllCustom();
}
