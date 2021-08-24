package com.rafdev.springboot.restful.api.demo;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "SpringBoot RESTful API demo",
                description = "SpringBoot RESTful API demo based on blog",
                version = "1.0"
        )
)
public class SpringbootRestfulApiDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootRestfulApiDemoApplication.class, args);
    }

}
