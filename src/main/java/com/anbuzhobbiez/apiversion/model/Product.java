package com.anbuzhobbiez.apiversion.model;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Details about a product in the catalog")
public record Product(
        @Schema(description = "Unique identifier", example = "1")
        Long id,

        @Schema(description = "Product name", example = "Gaming Laptop")
        String name,

        @Schema(description = "Detailed product description", example = "High-performance laptop with RGB keyboard")
        String description,

        @Schema(description = "Unit price", example = "1200.00")
        Double price
) {}