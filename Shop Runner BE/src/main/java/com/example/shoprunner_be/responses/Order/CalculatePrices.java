package com.example.shoprunner_be.responses.Order;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class CalculatePrices {
    BigDecimal price;
    BigDecimal discountPercentage;
    BigDecimal totalPrice;

}
