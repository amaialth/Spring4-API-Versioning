package com.anbuzhobbiez.apiversion.controller.sb4;

import com.anbuzhobbiez.apiversion.dto.ProductV1;
import com.anbuzhobbiez.apiversion.dto.ProductV2;
import com.anbuzhobbiez.apiversion.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/query/products")
@Tag(name = "Query Product API", description = "Manage products with versioning via Query Parameter")
public class QueryParameterVersioning {
    private final ProductService productService;

    public QueryParameterVersioning(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "Get Products V1", description = "Legacy version without price field")
    @GetMapping(version = "1")
    public List<ProductV1> getAllProductsQueryV1(@RequestParam("version") String version) {
        return productService.getAllProducts().stream()
                .map(product -> new ProductV1(product.id(), product.name(), product.description()))
                .collect(Collectors.toList());
    }

    @Operation(summary = "Get Products V2", description = "Latest version with price field")
    @GetMapping(version = "2")
    public List<ProductV2> getAllProductsQueryV2(@RequestParam("version") String version) {
        return productService.getAllProducts().stream()
                .map(product -> new ProductV2(product.id(), product.name(), product.description(), product.price()))
                .collect(Collectors.toList());
    }
}
