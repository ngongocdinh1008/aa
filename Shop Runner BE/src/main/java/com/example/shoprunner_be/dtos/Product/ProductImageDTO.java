package com.example.shoprunner_be.dtos.Product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ProductImageDTO {
    @JsonProperty("product_id")
    Long productId;

    @JsonProperty("image_url")
    String imageUrl;
}
