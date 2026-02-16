package com.anbuzhobbiez.apiversion.dto;

import io.swagger.v3.oas.annotations.media.Schema;

// DTO for API v2
@Schema(name = "Product")
public record ProductV2(Long id, String name, String description, Double price) {
}
