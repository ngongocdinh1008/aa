package com.example.shoprunner_be.entitys;

import com.example.shoprunner_be.entitys.Product.Product;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "order")
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @OneToMany(mappedBy = "order", orphanRemoval = true)
    List<Product> products;

    @Column(name = "temporary_price")
    BigDecimal temporaryPrice;

    int disscount;

    @Column(name = "sum_price")
    BigDecimal sumPrice;

}
