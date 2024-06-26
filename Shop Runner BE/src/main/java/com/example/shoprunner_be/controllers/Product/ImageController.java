package com.example.shoprunner_be.controllers.Product;

import com.example.shoprunner_be.dtos.Product.ProductImageDTO;
import com.example.shoprunner_be.entitys.Product.Product;
import com.example.shoprunner_be.entitys.Product.ProductImage;
import com.example.shoprunner_be.exceptions.EntityNotFoundException;
import com.example.shoprunner_be.exceptions.InappropriateDataException;
import com.example.shoprunner_be.repositories.ProductImageRepo;
import com.example.shoprunner_be.repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/product/{productId}/images")
public class ImageController {
    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private ProductImageRepo productImageRepo;

    @PostMapping
    public ResponseEntity<?> uploadImage(
            @PathVariable Long productId,
            @RequestParam("images") MultipartFile[] images) throws IOException {

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id " + productId));

        List<ProductImage> productImages = new ArrayList<>();
        for (MultipartFile image : images) {
            if (image.isEmpty()) {
                throw new InappropriateDataException("Image cannot be empty.");
            }

            if (image.getSize() > 20 * 1024 * 1024) {
                throw new InappropriateDataException("Image size exceeds the allowed limit (10MB).");
            }

            String contentType = image.getContentType();
            if (!contentType.startsWith("image/jpeg") && !contentType.startsWith("image/png")) {
                throw new InappropriateDataException("Unsupported image type: " + contentType);
            }
            Path uploadPath = Paths.get("upload");
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String filename = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
            String imageUrl = "/upload/" + filename;
            Path filePath = Paths.get("upload", filename);
            Files.copy(image.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            productImages.add(new ProductImage(product, imageUrl));
        }

        productImageRepo.saveAll(productImages);

        return ResponseEntity.ok("Images uploaded successfully");
    }

    @GetMapping
    public ResponseEntity<List<ProductImageDTO>> getProductImages(@PathVariable Long productId) {
        List<ProductImage> productImages = productImageRepo.findByProductId(productId);

        if (productImages == null || productImages.isEmpty()) {
            throw new EntityNotFoundException("No images found for product with id " + productId);
        }

        List<ProductImageDTO> productImageDTOs = productImages.stream()
                .map(productImage -> new ProductImageDTO(productImage.getId(), productImage.getImageUrl()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(productImageDTOs);
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
