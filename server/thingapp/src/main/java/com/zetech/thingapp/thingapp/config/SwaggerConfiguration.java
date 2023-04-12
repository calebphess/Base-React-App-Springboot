package com.zetech.thingapp.thingapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/*
 * This config enables us to autmagically set up swagger on all of our endpoints
 */

//@Configuration
@EnableSwagger2
@EnableWebMvc
public class SwaggerConfiguration
{
  @Bean
  public Docket api()
  {
    return new Docket(DocumentationType.SWAGGER_2).select()
        .apis(RequestHandlerSelectors.basePackage("com.zetech.thingapp.thingapp.web.endpoint"))
        .paths(PathSelectors.regex("/.*"))
        .build()
        .apiInfo(apiEndPointsInfo());
  }

  private ApiInfo apiEndPointsInfo()
  {
    return new ApiInfoBuilder()
        .title("Thing App REST API")
        .description("Thing App REST API")
        .version("1.0.0")
        .build();
  }
}