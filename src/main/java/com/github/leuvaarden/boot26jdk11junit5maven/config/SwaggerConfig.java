package com.github.leuvaarden.boot26jdk11junit5maven.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI(Info info) {
        return new OpenAPI().info(info);
    }

    @Bean
    public Info info(BuildProperties buildProperties) {
        return new Info()
                .title(buildProperties.getName())
                .description(buildProperties.getGroup() + ":" + buildProperties.getArtifact())
                .version(buildProperties.getVersion());
    }
}
