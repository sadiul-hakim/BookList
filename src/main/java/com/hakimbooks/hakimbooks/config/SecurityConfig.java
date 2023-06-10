package com.hakimbooks.hakimbooks.config;

import com.hakimbooks.hakimbooks.security.AuthenticationInterceptor;
import com.hakimbooks.hakimbooks.security.Role;
import com.hakimbooks.hakimbooks.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationInterceptor authenticationInterceptor;
    @Bean
    protected SecurityFilterChain config(HttpSecurity http) throws Exception {
        String[] publicGetRequestWhiteLis={
                "/api/user/{userId}",
                "/api/bookName/**",
                "/api/book/**",
                "/api/readBook/**",
                "/api/user/status/{userId}"
        };

        String[] privateGetRequestWhiteLis={
                "/api/user/**",
                "/api/bookName/**",
                "/api/book/**",
                "/api/readBook/**",
                "/api/category/{categoryId}",
                "/api/category/list",
                "/api/category/category/{categoryId}"
        };

        String[] publicPostRequestWhiteLis={
                "/api/bookName/post",
                "/api/book/post",
                "/api/readBook/post"
        };

        String[] publicDeleteRequestWhiteLis={
                "/api/book/{bookId}",
                "/api/readBook/{readBookId}"
        };

        String[] privateDeleteRequestWhiteLis={
                "/api/user/{userId}",
                "/api/book/{bookId}",
                "/api/bookName/{nameId}",
                "/api/readBook/{readBookId}",
                "/api/category/{categoryId}"
        };

        String[] privatePostRequestWhiteList={
                "/api/category/post"
        };

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/api/auth/**")
                                .permitAll()
                                .requestMatchers(HttpMethod.GET,publicGetRequestWhiteLis)
                                .hasAnyRole(Role.USER.name())
                                .requestMatchers(HttpMethod.POST,publicPostRequestWhiteLis)
                                .hasAnyRole(Role.USER.name())
                                .requestMatchers(HttpMethod.DELETE,publicDeleteRequestWhiteLis)
                                .hasRole(Role.USER.name())
                                .requestMatchers(HttpMethod.GET,privateGetRequestWhiteLis)
                                .hasRole(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.POST,privatePostRequestWhiteList)
                                .hasRole(Role.ADMIN.name())
                                .requestMatchers(HttpMethod.DELETE,privateDeleteRequestWhiteLis)
                                .hasRole(Role.ADMIN.name())
                                .anyRequest()
                                .authenticated()
                )
                .authenticationProvider(daoAuthenticationProvider())
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(authenticationInterceptor, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        final CorsConfiguration configuration=new CorsConfiguration();
        configuration.setAllowedOrigins(
                List.of("http://localhost:3000")
        );
        configuration.setAllowedMethods(
                List.of("GET","POST","DELETE")
        );
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(
                List.of("Authorization", "Cache-Control", "Content-Type")
        );
        final UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);
        return source;
    }

    @Bean
    protected AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider=new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(customUserDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }
    @Bean
    protected PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
