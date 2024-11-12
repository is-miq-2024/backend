package org.example.configurations

import org.example.services.AuthService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
@EnableWebSecurity
@Import(PasswordEncoderConfig::class, AuthService::class)
class SecurityConfig {

    @Bean
    fun corsConfigurer(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**")
                    .allowedOrigins("*")
                    .allowedMethods("PUT", "DELETE",
                        "GET", "POST")
                    .allowedHeaders("*")
            }
        }
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .cors { it.disable() }
            .csrf { it.disable() }
            .authorizeHttpRequests { authz ->
                authz
                    .requestMatchers("/auth/register", "/auth/login", "/route/search", "/route/{id}", "/swagger-ui/index.html")
                    .permitAll()
                    .anyRequest().authenticated()
            }
            .httpBasic { basicAuth -> basicAuth.realmName("Travel App") }
            .logout { logout ->
                logout.logoutUrl("/auth/logout")
                    .logoutSuccessHandler(LogoutSuccessHandler { _, response, _ ->
                        response.status = HttpStatus.OK.value()
                        response.writer.write("{\"message\": \"Logout successful!\"}")
                    })
            }

        return http.build()
    }
}

