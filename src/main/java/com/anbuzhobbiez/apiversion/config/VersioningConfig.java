package com.anbuzhobbiez.apiversion.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ApiVersionConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class VersioningConfig implements WebMvcConfigurer {

    @Override
    public void configureApiVersioning(ApiVersionConfigurer configure) {
        configure
                .addSupportedVersions("1.0", "2.0")
                .setDefaultVersion("1.0")
                .useVersionResolver(new CustomApiVersionResolver())
                //.usePathSegment(1)
                 .useRequestHeader("X-API-Version")
                 .useQueryParam("version")
                 .useMediaTypeParameter(MediaType.APPLICATION_JSON, "version");
    }

}
