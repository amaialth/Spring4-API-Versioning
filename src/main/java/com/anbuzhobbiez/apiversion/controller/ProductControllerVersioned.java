package com.anbuzhobbiez.apiversion.controller;

import com.anbuzhobbiez.apiversion.dto.ProductV1;
import com.anbuzhobbiez.apiversion.dto.ProductV2;
import com.anbuzhobbiez.apiversion.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

////@RestController
@Tag(name = "Product API", description = "Manage product catalog across different versions")
public class ProductControllerVersioned {
    private final ProductService productService;

    public ProductControllerVersioned(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Get Products V1", description = "Legacy endpoint: Returns products without pricing information.")
    @GetMapping(path = "/products", version = "1")
    public List<ProductV1> getAllProductsV1() {
        return productService.getAllProducts().stream()
                .map(product -> new ProductV1(product.id(), product.name(), product.description()))
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get Products V2", description = "Current endpoint: Returns products including the new price field.")
    @GetMapping(path = "/products", version = "2")
    public List<ProductV2> getAllProductsV2() {
        return productService.getAllProducts().stream()
                .map(product -> new ProductV2(product.id(), product.name(), product.description(), product.price()))
                .collect(Collectors.toList());
    }
}
