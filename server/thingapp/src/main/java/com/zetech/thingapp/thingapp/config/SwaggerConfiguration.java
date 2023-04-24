// package com.zetech.thingapp.thingapp.config;

// import org.springframework.context.annotation.Bean;
// import org.springframework.context.annotation.Configuration;

// import springfox.documentation.builders.ApiInfoBuilder;
// import springfox.documentation.builders.PathSelectors;
// import springfox.documentation.builders.RequestHandlerSelectors;
// import springfox.documentation.service.ApiInfo;
// import springfox.documentation.spi.DocumentationType;
// import springfox.documentation.spring.web.plugins.Docket;

// /*
//  * This config enables us to autmagically set up swagger on all of our endpoints
//  */

//  // TODO: get this working

// @Configuration
// // @ConditionalOnProperty(value = "springfox.documentation.enabled", havingValue = "true", matchIfMissing = true)
// public class SwaggerConfiguration {
//     @Bean
//     public Docket createRestApi() {
//         return new Docket(DocumentationType.OAS_30)
//                 .apiInfo(apiInfo())
//                 .select()
//                 .apis(RequestHandlerSelectors.basePackage("com.zetech.thingapp.thingapp.web.endpoint"))
//                 .paths(PathSelectors.regex("/.*"))
//                 .build();
//     }

//     private ApiInfo apiInfo() {
//         return new ApiInfoBuilder()
//           .title("Thing App REST API")
//           .description("Thing App REST API")
//           // .contact(new Contact("Penn Hess", "https://github.com/calebphess", "pennhess@thingapp.com"))
//           .version("1.0.0")
//           .build();
//     }
// }