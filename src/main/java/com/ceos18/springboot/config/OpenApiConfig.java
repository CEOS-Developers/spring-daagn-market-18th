package com.ceos18.springboot.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger springdoc-ui 구성 파일
 */
@Configuration
@SecurityScheme(
		name = "Authorization",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "bearer"
)
public class OpenApiConfig {
	@Bean
	public OpenAPI openAPI() {
		Info info = new Info()
				.title("API Document")
				.version("v0.0.1")
				.description("API 명세서입니다.");
		return new OpenAPI()
				.components(new Components())
				.info(info);
	}
}