package com.example.shoprunner_be.responses.Product;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ProductResponse {
    Long id;

    String name;

    BigDecimal price;

    int discount;

    String description;

    boolean idFavorite;

    List<ProductColorResponse> colors;

    List<ProductSizeResponse> sizes;

    String category;
}
