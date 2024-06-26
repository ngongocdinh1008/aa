package com.example.shoprunner_be.services.Order;

import com.example.shoprunner_be.entitys.Order;
import com.example.shoprunner_be.responses.Order.CalculatePrices;

public interface OrderService {

    Order addProducttoOrder(Long orderId, Long productId);

    Order removeProductfromOrder(Long orderId, Long productId);

    CalculatePrices calculatePrices(Order order);

    void deleteOrder(Long orderId);
}
