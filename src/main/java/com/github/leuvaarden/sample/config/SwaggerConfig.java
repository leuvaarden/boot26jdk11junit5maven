package com.github.leuvaarden.sample.config;

import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.Resource;
import java.util.Collections;

@EnableSwagger2
@EnableOpenApi
@Profile("swagger")
@Configuration
public class SwaggerConfig {

    @Resource
    private BuildProperties buildProperties;

    @Bean
    public ApiInfo apiInfo() {
        return new ApiInfo(buildProperties.getName(), buildProperties.getGroup() + buildProperties.getArtifact(), buildProperties.getVersion(), null, null, null, null, Collections.emptyList());
    }
}
