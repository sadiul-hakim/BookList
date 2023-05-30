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

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationInterceptor authenticationInterceptor;
    @Bean
    protected SecurityFilterChain config(HttpSecurity http) throws Exception {
        String[] getRequestWhiteLis={
                "/api/user/**",
                "/api/bookName/**",
                "/api/book/**",
                "/api/readBook"
        };

        String[] userPostRequestWhiteLis={
                "/api/bookName/**",
                "/api/book/**",
                "/api/readBook"
        };
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/api/auth/**")
                                .permitAll()
                                .requestMatchers(HttpMethod.GET,getRequestWhiteLis)
                                .hasAnyRole(Role.USER.name(),Role.ADMIN.name())
                                .requestMatchers(HttpMethod.POST,userPostRequestWhiteLis)
                                .hasAnyRole(Role.USER.name())
                                .requestMatchers(HttpMethod.DELETE,"/api/user/**")
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
