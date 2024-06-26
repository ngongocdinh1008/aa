package com.example.shoprunner_be.dtos.Product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ProductColorDTO {
    @JsonProperty("product_id")
    Long productId;

    String color;
}
