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

@RestController
@RequestMapping("/api")
@Tag(name = "Native Path Versioning", description = "Uses SB4 native path segment extraction (e.g., /v1/products)")
public class PathBasedVersioning {

        private final ProductService productService;

        public PathBasedVersioning(ProductService productService) {
                this.productService = productService;
        }

        @Operation(summary = "Get Products V1 (Legacy)", description = "Accessed via /v1/products. Returns basic product details without pricing.", responses = {
                        @ApiResponse(responseCode = "200", description = "Successful retrieval of v1 schema", content = @Content(schema = @Schema(implementation = ProductV1.class)))
        })
        @GetMapping(value = "/{version}/products",version = "1")
        public List<ProductV1> getV1() {
                return productService.getAllProducts().stream()
                                .map(p -> new ProductV1(p.id(), p.name(), p.description()))
                                .toList();
        }

        @Operation(summary = "Get Products V2 (Latest)", description = "Accessed via /v2/products. Includes the new 'price' field.", responses = {
                        @ApiResponse(responseCode = "200", description = "Successful retrieval of v2 schema", content = @Content(schema = @Schema(implementation = ProductV2.class)))
        })
        @GetMapping(value = "/{version}/products", version = "2")
        public List<ProductV2> getV2() {
                return productService.getAllProducts().stream()
                                .map(p -> new ProductV2(p.id(), p.name(), p.description(), p.price()))
                                .toList();
        }
}
