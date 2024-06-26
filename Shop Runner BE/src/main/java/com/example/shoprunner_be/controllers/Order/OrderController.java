package com.example.shoprunner_be.controllers.Order;

import com.example.shoprunner_be.entitys.Order;
import com.example.shoprunner_be.exceptions.EntityNotFoundException;
import com.example.shoprunner_be.repositories.OrderRepo;
import com.example.shoprunner_be.responses.Order.CalculatePrices;
import com.example.shoprunner_be.responses.Order.OrderDetail;
import com.example.shoprunner_be.services.Order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepo orderRepo;

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDetail> getOrder(@PathVariable("orderId") Long orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id " + orderId));
        return ResponseEntity.ok(OrderDetail.builder()
                .orderId(order.getId())
                .products(order.getProducts())
                .temporaryPrice(order.getTemporaryPrice())
                .discount(order.getDisscount())
                .sumPrice(order.getSumPrice())
                .build());
    }

    @PostMapping("/{productId}")
    public ResponseEntity<?> addProducttoOrder(Long productId, Long orderId) {
        orderService.addProducttoOrder(productId, orderId);
        return ResponseEntity.ok().body("Successfully added product to order");
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> removeProductfromOrder(Long productId, Long orderId) {
        orderService.removeProductfromOrder(productId, orderId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{orderId}/calculate-prices")
    public ResponseEntity<CalculatePrices> calculatePrices(@PathVariable Long orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id " + orderId));
        CalculatePrices calculatePrices = orderService.calculatePrices(order);
        return ResponseEntity.ok(calculatePrices);
    }
    @DeleteMapping("/{orderId}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok().body("Successfully deleted order");
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}