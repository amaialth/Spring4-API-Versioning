package com.anbuzhobbiez.apiversion.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.accept.ApiVersionResolver;

public class CustomApiVersionResolver implements ApiVersionResolver {

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    @Override
    public String resolveVersion(HttpServletRequest request) {
        String path = request.getRequestURI();

        // 1. Bypass version check for Swagger/OpenAPI
        if (pathMatcher.match("/**/swagger-ui/**", path) ||
                pathMatcher.match("/**/api-docs/**", path) ||
                pathMatcher.match("/docs/**", path)) {
            return "1.0";
        }
        return null; // Fallback to other strategies (Header, Query, etc.)
    }
}
