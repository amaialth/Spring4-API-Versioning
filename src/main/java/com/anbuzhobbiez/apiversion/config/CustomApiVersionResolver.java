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
        // 2. Query Parameter
        String queryVersion = request.getParameter("version");
        if (queryVersion != null && !queryVersion.isEmpty()) {
            return queryVersion;
        }

        // 3. Header
        String headerVersion = request.getHeader("X-API-Version");
        if (headerVersion != null && !headerVersion.isEmpty()) {
            return headerVersion;
        }

        // 4. Accept Header (Content Negotiation)
        String acceptHeader = request.getHeader("Accept");
        if (acceptHeader != null) {
            if (acceptHeader.contains("app-v1+json"))
                return "1.0";
            if (acceptHeader.contains("app-v2+json"))
                return "2.0";
        }

        // 5. Path Pattern (Naive extraction for /v1/ or /v2/)
        if (path.matches("(?i).*/v1/.*"))
            return "1.0";
        if (path.matches("(?i).*/v2/.*"))
            return "2.0";

        return null; // Fallback to other strategies (Header, Query, etc.)
    }
}
