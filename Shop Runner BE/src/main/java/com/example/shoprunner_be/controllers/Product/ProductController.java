package com.example.shoprunner_be.controllers.Product;

import com.example.shoprunner_be.dtos.Product.ProductColorDTO;
import com.example.shoprunner_be.dtos.Product.ProductDTO;
import com.example.shoprunner_be.dtos.Product.ProductSizeDTO;
import com.example.shoprunner_be.entitys.Product.Product;
import com.example.shoprunner_be.exceptions.EntityNotFoundException;
import com.example.shoprunner_be.exceptions.InappropriateDataException;
import com.example.shoprunner_be.responses.Product.ProductColorResponse;
import com.example.shoprunner_be.responses.Product.ProductResponse;
import com.example.shoprunner_be.responses.Product.ProductSizeResponse;
import com.example.shoprunner_be.responses.ResponseObject;
import com.example.shoprunner_be.services.Product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/product")
public class ProductController {
     @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductResponse> productResponses = products.stream()
                .map(product -> {
                    List<ProductColorResponse> colorResponses = product.getColors().stream()
                            .map(productColor -> new ProductColorResponse(productColor.getColor()))
                            .collect(Collectors.toList());

                    List<ProductSizeResponse> sizeResponses = product.getSizes().stream()
                            .map(productSize -> new ProductSizeResponse(productSize.getSize()))
                            .collect(Collectors.toList());

                    return ProductResponse.builder()
                            .id(product.getId())
                            .name(product.getName())
                            .price(product.getPrice())
                            .discount(product.getDiscount())
                            .description(product.getDescription())
                            .idFavorite(product.isFavorite())
                            .colors(colorResponses)
                            .sizes(sizeResponses)
                            .category(product.getCategory().getName())
                            .build();
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(productResponses);
    }

    @GetMapping("/{id}")
    private ResponseEntity<ProductResponse> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);

        ProductResponse productResponse = ProductResponse
                .builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .discount(product.getDiscount())
                .description(product.getDescription())
                .idFavorite(product.isFavorite())
                .colors(product.getColors().stream()
                        .map(productColor -> new ProductColorResponse(productColor.getColor()))
                        .collect(Collectors.toList()))
                .category(product.getCategory().getName())
                .sizes(product.getSizes().stream()
                        .map(productSize -> new ProductSizeResponse(productSize.getSize()))
                        .collect(Collectors.toList()))
                .category(product.getCategory().getName())
                .build();
        return ResponseEntity.ok(productResponse);
    }

    @GetMapping("/category/{id}")
    private List<Product> getProductByCategory(@PathVariable Long id) {
        return productService.getProductByCategoryId(id);
    }

    @PostMapping
    public ResponseEntity<ResponseObject> addProduct(@RequestBody ProductDTO productDTO) {
        Product addProduct = productService.addProduct(productDTO);
        return ResponseEntity.ok(ResponseObject.builder()
                .message("Create new product successfully")
                .status(HttpStatus.CREATED)
                .data(addProduct)
                .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseObject> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        Product updateProduct = productService.updateProduct(id, productDTO);
        return ResponseEntity.ok(ResponseObject
                .builder()
                .message("Update product successfully")
                .status(HttpStatus.OK)
                .data(updateProduct)
                .build());
    }

    @PostMapping("/{productId}/favorite")
    public void updateFavoriteProduct(@PathVariable Long productId) {
        productService.updateFavorite(productId);
    }

    @DeleteMapping("/{productId}/favorite")
    public void deleteFavoriteProduct(@PathVariable Long productId) {
        productService.deleteFavorite(productId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseObject> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok(ResponseObject
                .builder()
                .message("Delete product successfully")
                .status(HttpStatus.OK)
                .data(null)
                .build());
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
    @ExceptionHandler(InappropriateDataException.class)
    public ResponseEntity<String> handleUnsupportedImageTypeException(InappropriateDataException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }
}
