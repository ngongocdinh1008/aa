package com.example.shoprunner_be.responses.Order;

import com.example.shoprunner_be.entitys.Product.Product;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class OrderDetail {
    Long orderId;
    List<Product> products;
    BigDecimal temporaryPrice;
    int discount;
    BigDecimal sumPrice;
}
