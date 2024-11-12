package org.example.configurations

import org.example.services.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.HttpStatusEntryPoint
import org.springframework.http.HttpStatus

@Configuration
@EnableWebSecurity
@Import(PasswordEncoderConfig::class, AuthService::class)
class SecurityConfig(
    private val authService: AuthService,
    private val passwordEncoder: PasswordEncoder
) {

    @Autowired
    fun configure(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(authService).passwordEncoder(passwordEncoder)
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() }
            .authorizeHttpRequests { authz ->
                authz
                    .requestMatchers("/auth/register", "/auth/login", "/route/search")
                    .permitAll()
                    .anyRequest().authenticated()
            }
            .formLogin { form -> form.defaultSuccessUrl("/route/search", true) }

        return http.build()
    }
}
