package com.anbuzhobbiez.apiversion.config;

import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.MediaType;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;

@Configuration
public class OpenApiConfig {

    @Bean
    public GroupedOpenApi v1Api() {
        return GroupedOpenApi.builder()
                .group("v1")
                .pathsToMatch("/**")
                .addOperationCustomizer((operation, handlerMethod) -> {
                    var mapping = handlerMethod.getMethodAnnotation(GetMapping.class);
                    if (mapping != null) {
                        // Check version attribute
                        if ("1".equals(mapping.version())) {
                            updateMediaType(operation, "application/json;version=1");
                            return operation;
                        }
                        // Check params
                        if (mapping.params() != null) {
                            for (String param : mapping.params()) {
                                if ("version=1".equals(param)) {
                                    updateMediaType(operation, "application/json;version=1");
                                    return operation;
                                }
                            }
                        }
                        // Check produces (Content Negotiation)
                        if (mapping.produces() != null) {
                            for (String produce : mapping.produces()) {
                                if (produce.contains("v1")) {
                                    return operation;
                                }
                            }
                        }
                        // Check path patterns
                        if (mapping.value() != null) {
                            for (String path : mapping.value()) {
                                if (path.contains("/v1/")) {
                                    return operation;
                                }
                            }
                        }
                        if (mapping.path() != null) {
                            for (String path : mapping.path()) {
                                if (path.contains("/v1/")) {
                                    return operation;
                                }
                            }
                        }
                    }
                    return null;
                }).build();
    }

    @Bean
    public GroupedOpenApi v2Api() {
        return GroupedOpenApi.builder()
                .group("v2")
                .pathsToMatch("/**")
                .addOperationCustomizer((operation, handlerMethod) -> {
                    var mapping = handlerMethod.getMethodAnnotation(GetMapping.class);
                    if (mapping != null) {
                        // Check version attribute
                        if ("2".equals(mapping.version())) {
                            updateMediaType(operation, "application/json;version=2");
                            return operation;
                        }
                        // Check params
                        if (mapping.params() != null) {
                            for (String param : mapping.params()) {
                                if ("version=2".equals(param)) {
                                    updateMediaType(operation, "application/json;version=2");
                                    return operation;
                                }
                            }
                        }
                        // Check produces (Content Negotiation)
                        if (mapping.produces() != null) {
                            for (String produce : mapping.produces()) {
                                if (produce.contains("v2")) {
                                    return operation;
                                }
                            }
                        }
                        // Check path patterns
                        if (mapping.value() != null) {
                            for (String path : mapping.value()) {
                                if (path.contains("/v2/")) {
                                    return operation;
                                }
                            }
                        }
                        if (mapping.path() != null) {
                            for (String path : mapping.path()) {
                                if (path.contains("/v2/")) {
                                    return operation;
                                }
                            }
                        }
                    }
                    return null;
                })
                .build();
    }

    private void updateMediaType(io.swagger.v3.oas.models.Operation operation, String mediaTypeName) {
        if (operation.getResponses() != null) {
            operation.getResponses().forEach((status, response) -> {
                Content content = response.getContent();
                if (content != null) {
                    MediaType mediaType = content.get("application/json");
                    if (mediaType == null) {
                        // Fallback: If application/json is missing, try generic wildcard
                        mediaType = content.get("*/*");
                    }

                    if (mediaType != null) {
                        content.remove("application/json");
                        content.remove("*/*");
                        content.addMediaType(mediaTypeName, mediaType);
                    }
                }
            });
        }
    }
}
