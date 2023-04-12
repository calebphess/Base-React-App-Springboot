// package com.zetech.thingapp.thingapp.config;

// import org.springframework.context.annotation.Configuration;
// import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

// TODO: unfuck this after Swagger is fixed

// @Configuration
// @EnableWebSecurity
// @EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
// public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

//     private static final String[] AUTH_WHITELIST = {
//             // -- Swagger UI v2
//             "/v2/api-docs",
//             "/swagger-resources",
//             "/swagger-resources/**",
//             "/configuration/ui",
//             "/configuration/security",
//             "/swagger-ui.html",
//             "/webjars/**",
//             // -- Swagger UI v3 (OpenAPI)
//             "/v3/api-docs/**",
//             "/swagger-ui/**"
//             // other public endpoints of your API may be appended to this array
//     };


//     @Override
//     protected void configure(HttpSecurity http) throws Exception {
//         http.
//                 // ... here goes your custom security configuration
//                 authorizeRequests().
//                 antMatchers(AUTH_WHITELIST).permitAll().  // whitelist Swagger UI resources
//                 // ... here goes your custom security configuration
//                 antMatchers("/**").authenticated();  // require authentication for any endpoint that's not whitelisted
//     }

// }