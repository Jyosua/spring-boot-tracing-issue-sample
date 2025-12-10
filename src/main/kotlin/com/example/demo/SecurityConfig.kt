package com.example.demo

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.web.SecurityFilterChain
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Value("\${spring.security.oauth2.resourceserver.jwt.issuer-uri:}")
    private val issuerUri: String = ""

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/health").permitAll()
                    .anyRequest().authenticated()
            }
            .oauth2ResourceServer { oauth2 ->
                oauth2.jwt { }
            }
        
        return http.build()
    }

    @Bean
    fun jwtDecoder(): JwtDecoder {
        return if (issuerUri.isNotEmpty()) {
            NimbusJwtDecoder.withIssuerLocation(issuerUri).build()
        } else {
            // Default decoder for development/testing - uses HMAC SHA-256
            val secret = "default-secret-key-for-development-must-be-at-least-256-bits-long"
            val secretKey: SecretKey = SecretKeySpec(secret.toByteArray(), "HmacSHA256")
            NimbusJwtDecoder.withSecretKey(secretKey).build()
        }
    }
}
