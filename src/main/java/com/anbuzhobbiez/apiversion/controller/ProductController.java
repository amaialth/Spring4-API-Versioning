package com.anbuzhobbiez.apiversion.controller;

import com.anbuzhobbiez.apiversion.model.Product;
import com.anbuzhobbiez.apiversion.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@Tag(name = "Product Management", description = "Operations related to the product catalog")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(
            summary = "Get all products",
            description = "Retrieves a complete list of products from the in-memory catalog.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Successful retrieval",
                            content = @Content(schema = @Schema(implementation = Product.class))
                    )
            }
    )
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
}
