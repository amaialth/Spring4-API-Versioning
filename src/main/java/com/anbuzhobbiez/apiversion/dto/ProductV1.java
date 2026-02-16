package com.anbuzhobbiez.apiversion.dto;

import io.swagger.v3.oas.annotations.media.Schema;

// DTO for API v1
@Schema(name = "Product")
public record ProductV1(Long id, String name, String description) {
}
