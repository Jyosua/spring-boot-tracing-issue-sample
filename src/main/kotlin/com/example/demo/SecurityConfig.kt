package com.example.demo

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.security.web.SecurityFilterChain
import java.nio.charset.StandardCharsets
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Value("\${spring.security.oauth2.resourceserver.jwt.issuer-uri:}")
    private val issuerUri: String? = null

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            authorizeHttpRequests {
                authorize("/health", permitAll)
                authorize(anyRequest, authenticated)
            }
            oauth2ResourceServer {
                jwt { }
            }
            csrf {
                disable()
            }
            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }
        }
        
        return http.build()
    }

    @Bean
    fun jwtDecoder(): JwtDecoder {
        return if (!issuerUri.isNullOrEmpty()) {
            NimbusJwtDecoder.withIssuerLocation(issuerUri).build()
        } else {
            val secret = System.getenv("JWT_SECRET") 
                ?: throw IllegalStateException(
                    "JWT configuration required: Set either 'spring.security.oauth2.resourceserver.jwt.issuer-uri' " +
                    "property or 'JWT_SECRET' environment variable for development"
                )
            val secretKey: SecretKey = SecretKeySpec(secret.toByteArray(StandardCharsets.UTF_8), "HmacSHA256")
            NimbusJwtDecoder.withSecretKey(secretKey).build()
        }
    }
}
