package com.example.shoprunner_be.services.Order;

import com.example.shoprunner_be.entitys.Order;
import com.example.shoprunner_be.entitys.Product.Product;
import com.example.shoprunner_be.exceptions.EntityNotFoundException;
import com.example.shoprunner_be.repositories.OrderRepo;
import com.example.shoprunner_be.repositories.ProductRepo;
import com.example.shoprunner_be.responses.Order.CalculatePrices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class OrderImp implements OrderService{
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private OrderRepo orderRepo;

    @Transactional
    public Order addProducttoOrder(Long orderId, Long productId) {
        Order order;
        if (orderId != null){
            order = orderRepo.findById(orderId)
                    .orElseThrow(() -> new EntityNotFoundException("Order not found with id " + orderId));
        }else {
            order = new Order();
            orderRepo.save(order);
        }
        Product product = productRepo.findById(productId)
                .orElseThrow(()-> new EntityNotFoundException("Product not found with id: " + productId));
        product.setOrder(order);
        order.getProducts().add(product);
        calculatePrices(order);
        return orderRepo.save(order);
    }

    @Transactional
    public Order removeProductfromOrder(Long orderId, Long productId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(()-> new EntityNotFoundException("Order not found with id " + orderId));
        Product productToRemove = order.getProducts().stream()
                .filter(product -> product.getId().equals(productId))
                .findFirst()
                .orElseThrow(()-> new EntityNotFoundException("Product not found with id: " + productId));
        order.getProducts().remove(productToRemove);
        productToRemove.setOrder(null);
        calculatePrices(order);
        return orderRepo.save(order);
    }

    @Override
    public CalculatePrices calculatePrices(Order order) {
        BigDecimal temporaryPrice = BigDecimal.ZERO;
        BigDecimal totalDiscount = BigDecimal.ZERO;
        for (Product product : order.getProducts()) {
            BigDecimal productPrice = product.getPrice();
            temporaryPrice = temporaryPrice.add(productPrice);

            BigDecimal Disscount = BigDecimal.valueOf(order.getDisscount());
            if (Disscount.compareTo(BigDecimal.ZERO) > 0) {
                totalDiscount = totalDiscount.add(productPrice.multiply(Disscount).divide(BigDecimal.valueOf(100)));
            }
        }
        BigDecimal discountPercentage = BigDecimal.ZERO;
        if (temporaryPrice.compareTo(BigDecimal.ZERO) > 0) {
            discountPercentage = totalDiscount.divide(temporaryPrice, 2, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
        }
        return CalculatePrices.builder()
                .price(temporaryPrice)
                .discountPercentage(discountPercentage)
                .totalPrice(totalDiscount)
                .build();
    }

    @Override
    public void deleteOrder(Long orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(()-> new EntityNotFoundException("Order not found with id " + orderId));
        orderRepo.delete(order);
    }
}
