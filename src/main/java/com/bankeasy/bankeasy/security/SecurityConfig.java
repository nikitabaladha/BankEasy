//package com.bankeasy.bankeasy.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.web.cors.CorsConfiguration;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public JwtAuthenticationFilter jwtAuthenticationFilter() {
//        return new JwtAuthenticationFilter();
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf(csrf -> csrf.disable())
//            .authorizeHttpRequests(authorizeRequests ->
//                authorizeRequests
//                    .requestMatchers("/api/auth/**").permitAll() 
//                    .requestMatchers("/api/users/**").authenticated()
//                    .requestMatchers("/api/accounts/**").authenticated()
//                    .requestMatchers("/api/profiles/**").authenticated()
//                    .requestMatchers("/api/beneficiaries/**").authenticated()
//                    .requestMatchers("/api/transactions/**").authenticated()
//                    .requestMatchers("/api/transfers/**").authenticated()
//                    .requestMatchers("/api/kyc/**").authenticated()
//                    
//                   
//                    .anyRequest().permitAll() 
//            )
//            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
//            .cors(corsConfigurer -> corsConfigurer.configurationSource(request -> {
//                CorsConfiguration config = new CorsConfiguration();
//                config.addAllowedOrigin("http://localhost:3000");  
//                config.addAllowedMethod("*");
//                config.addAllowedHeader("*");
//                config.setAllowCredentials(true); 
//                return config;
//            
//            }));
//        return http.build();
//    }
//}
//


package com.bankeasy.bankeasy.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorizeRequests ->
                authorizeRequests
                    .requestMatchers("/api/auth/**").permitAll() 
                    .requestMatchers("/api/admin/**").hasRole("Admin")
                    .requestMatchers("/api/users/**").authenticated()
                    .requestMatchers("/api/accounts/**").authenticated()
                    .requestMatchers("/api/profiles/**").authenticated()
                    .requestMatchers("/api/beneficiaries/**").authenticated()
                    .requestMatchers("/api/transactions/**").authenticated()
                    .requestMatchers("/api/transfers/**").authenticated()
                    .requestMatchers("/api/kyc/**").authenticated()
                    .anyRequest().permitAll() 
            )
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .cors(corsConfigurer -> corsConfigurer.configurationSource(request -> {
                CorsConfiguration config = new CorsConfiguration();
                config.addAllowedOrigin("http://localhost:3000");  
                config.addAllowedMethod("*");
                config.addAllowedHeader("*");
                config.setAllowCredentials(true); 
                return config;
            
            }));
        return http.build();
    }
}

