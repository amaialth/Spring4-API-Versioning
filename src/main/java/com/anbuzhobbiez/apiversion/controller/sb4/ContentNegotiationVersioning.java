package com.anbuzhobbiez.apiversion.controller.sb4;

import com.anbuzhobbiez.apiversion.dto.ProductV1;
import com.anbuzhobbiez.apiversion.dto.ProductV2;
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
import java.util.stream.Collectors;

// ProductController.java (Content Negotiation Versioning)
@RestController
@RequestMapping("/api/products")
@Tag(name = "Product Content Negotiation", description = "Versioning via Media Types (Accept Header)")
public class ContentNegotiationVersioning {
        private final ProductService productService;

        public ContentNegotiationVersioning(ProductService productService) {
                this.productService = productService;
        }

        @Operation(summary = "Get Products V1", description = "Returns legacy product format using Media Type v1", responses = {
                        @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/vnd.company.app-v1+json", schema = @Schema(implementation = ProductV1.class)))
        })
        @GetMapping(produces = "application/vnd.company.app-v1+json", version = "1")
        public List<ProductV1> getAllProductsMediaV1() {
                return productService.getAllProducts().stream()
                                .map(product -> new ProductV1(product.id(), product.name(), product.description()))
                                .collect(Collectors.toList());
        }

        @Operation(summary = "Get Products V2", description = "Returns modern product format with price using Media Type v2", responses = {
                        @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/vnd.company.app-v2+json", schema = @Schema(implementation = ProductV2.class)))
        })
        @GetMapping(produces = "application/vnd.company.app-v2+json", version = "2")
        public List<ProductV2> getAllProductsMediaV2() {
                return productService.getAllProducts().stream()
                                .map(product -> new ProductV2(product.id(), product.name(), product.description(),
                                                product.price()))
                                .collect(Collectors.toList());
        }
}