package com.careerquestai.backend.config;

import com.careerquestai.backend.security.JwtAuthenticationEntryPoint;
import com.careerquestai.backend.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;


@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;


    /* =========================================================
       PASSWORD ENCODER
    ========================================================= */

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }


    /* =========================================================
       AUTHENTICATION MANAGER
    ========================================================= */

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration configuration) throws Exception {

        return configuration.getAuthenticationManager();
    }


    /* =========================================================
       CORS CONFIGURATION
    ========================================================= */

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration configuration =
                new CorsConfiguration();

        /*
         * Allow requests from the frontend.
         *
         * We are using allowedOriginPatterns instead of
         * allowedOrigins so that "*" works correctly.
         */
        configuration.setAllowedOriginPatterns(
                List.of("*")
        );


        /*
         * HTTP methods allowed from the frontend.
         */
        configuration.setAllowedMethods(
                List.of(
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "OPTIONS"
                )
        );


        /*
         * Headers allowed from the frontend.
         */
        configuration.setAllowedHeaders(
                List.of(
                        "Authorization",
                        "Content-Type",
                        "Accept"
                )
        );


        /*
         * We are using JWT through the Authorization header,
         * so browser credentials are not required here.
         */
        configuration.setAllowCredentials(false);


        /*
         * Register CORS configuration for every endpoint.
         */
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration(
                "/**",
                configuration
        );

        return source;
    }


    /* =========================================================
       SECURITY FILTER CHAIN
    ========================================================= */

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http

                /* =========================
                   CSRF
                ========================== */

                .csrf(csrf ->
                        csrf.disable()
                )


                /* =========================
                   CORS
                ========================== */

                .cors(Customizer.withDefaults())


                /* =========================
                   SESSION MANAGEMENT
                ========================== */

                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )


                /* =========================
                   EXCEPTION HANDLING
                ========================== */

                .exceptionHandling(exception ->
                        exception.authenticationEntryPoint(
                                jwtAuthenticationEntryPoint
                        )
                )


                /* =========================
                   AUTHORIZATION
                ========================== */

                .authorizeHttpRequests(auth -> auth

                        /*
                         * Public endpoints
                         */
                        .requestMatchers(
                                "/api/auth/**",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/api/ai/test"
                        ).permitAll()


                        /*
                         * Every other API requires authentication.
                         */
                        .anyRequest().authenticated()
                )


                /* =========================
                   JWT FILTER
                ========================== */

                .addFilterBefore(
                        jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class
                )


                /* =========================
                   HTTP BASIC
                ========================== */

                .httpBasic(Customizer.withDefaults());


        return http.build();
    }
}