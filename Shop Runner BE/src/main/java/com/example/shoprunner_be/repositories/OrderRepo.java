package com.example.shoprunner_be.repositories;

import com.example.shoprunner_be.entitys.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Long> {
}
