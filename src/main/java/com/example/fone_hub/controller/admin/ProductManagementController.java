package com.example.fone_hub.controller.admin;

import com.example.fone_hub.dto.request.ProductRequest;
import com.example.fone_hub.service.ImageProductService;
import com.example.fone_hub.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class ProductManagementController {
    private final ProductService productService;
    private final ImageProductService imageProductService;

    @PostMapping
    public ResponseEntity<String> addProduct(@RequestBody ProductRequest product) {
        productService.createProduct(product);
        return ResponseEntity.ok("Product created");
    }

    @PostMapping("/add-image")
    public void addProductImage(@RequestParam("image") MultipartFile image, @RequestParam("id") Long productId) {
        imageProductService.createImage(image, productId);
    }
}
