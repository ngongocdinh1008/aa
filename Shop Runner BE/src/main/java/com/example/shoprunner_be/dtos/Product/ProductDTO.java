package com.example.shoprunner_be.dtos.Product;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class ProductDTO {
    String name;

    @Min(value = 0, message = "Requires price to be greater than 0 !!")
    BigDecimal price;

    @Min(value = 0, message = "Discount request must be greater than 0% !!")
    @Max(value = 100, message = "Discount request must be less than 100 % !!")
    int discount;

    String description;

    @JsonProperty("category_id")
    Long categoryId;

    List<String> imageUrls;

    List<String> colors;

    List<String> sizes;
}
