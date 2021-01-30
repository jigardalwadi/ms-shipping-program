package com.ebay.api.v1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class MsShippingProgramApplication {

    public static final String V1_API_ROOT = "/api/v1/shipping/program";

    public static void main(String[] args) {
        SpringApplication.run(MsShippingProgramApplication.class, args);
    }



}
