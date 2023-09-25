package com.zetech.thingapp.thingapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.zetech.thingapp.thingapp.web.interceptors.UserTokenInterceptor;

// TODO: Comment this better later as this section can be a bit confusing

@Configuration
// @EnableWebMvc
@ComponentScan(basePackages = "com.zetech.thingapp.thingapp.**")
//@ComponentScan(basePackages = "com.zetech.thingapp.thingapp.web.endpoint")
public class WebConfiguration implements WebMvcConfigurer
{
  @Autowired
  UserTokenInterceptor userTokenInterceptor;

  @Override
  public void addInterceptors(InterceptorRegistry registry)
  {
    registry.addInterceptor(userTokenInterceptor);
  }

  @Bean
  public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryCustomizer()
  {
    return factory -> factory.setContextPath("/thingapp");
  }

  // https://spring.io/blog/2015/06/08/cors-support-in-spring-framework
  // @Override
  // public void addCorsMappings(CorsRegistry registry)
  // {
  //   registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE");
  // }

  // @Bean
  // public WebMvcConfigurer corsConfigurer() {
  //     return new WebMvcConfigurer() {
  //         @Override
  //         public void addCorsMappings(CorsRegistry registry) {
  //             registry.addMapping("/**").allowedOrigins("http://localhost:3000");
  //         }
  //     };
  // }

  @Override
  public void addCorsMappings(CorsRegistry registry)
  {
    registry.addMapping("/**").allowedOrigins("*");
  }

//  @Bean
//  public WebMvcConfigurer corsConfigurer()
//  {
//    return new WebMvcConfigurerAdapter()
//    {
//      @Override
//      public void addCorsMappings(CorsRegistry registry)
//      {
//        registry.addMapping("/**");
//      }
//    };
//  }  
}
