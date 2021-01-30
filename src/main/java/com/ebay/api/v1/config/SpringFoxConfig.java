package com.ebay.api.v1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static springfox.documentation.builders.PathSelectors.any;

@Configuration
public class SpringFoxConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).groupName("public-api")
                .apiInfo(apiInfo()).select().apis(RequestHandlerSelectors.withClassAnnotation(RestController.class)).paths(any()).build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("Shipping Program API")
                .description("API reference for developers")
                .termsOfServiceUrl("http://example.com").build();
    }
}
