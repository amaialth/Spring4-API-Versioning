package com.anbuzhobbiez.apiversion.controller.sb4;

import com.anbuzhobbiez.apiversion.dto.ProductV1;
import com.anbuzhobbiez.apiversion.dto.ProductV2;
import com.anbuzhobbiez.apiversion.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

// ProductController.java (Header Versioning)
@RestController
@RequestMapping("/api/header/products")
@Tag(name = "Header Product API", description = "Endpoints for managing products with Header Versioning")
public class HeaderVersioning {
    private final ProductService productService;

    public HeaderVersioning(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Get Products V1", description = "Returns basic product details. Requires X-API-Version=1 header.")
    @GetMapping(version = "1")
    public List<ProductV1> getAllProductsHeaderV1(@RequestHeader("X-API-Version") String version) {
        return productService.getAllProducts().stream()
                .map(product -> new ProductV1(product.id(), product.name(), product.description()))
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get Products V2", description = "Returns full product details including price. Requires X-API-Version=2 header.")
    @GetMapping(version = "2")
    public List<ProductV2> getAllProductsHeaderV2(@RequestHeader("X-API-Version") String version) {
        return productService.getAllProducts().stream()
                .map(product -> new ProductV2(product.id(), product.name(), product.description(), product.price()))
                .collect(Collectors.toList());
    }
}
