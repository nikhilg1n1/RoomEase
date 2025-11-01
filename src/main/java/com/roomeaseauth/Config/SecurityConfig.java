package com.roomeaseauth.Config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.swing.text.AbstractDocument;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws  Exception{
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login","/","/error","/v1/saveRooms","/v1/**").permitAll()
                         .anyRequest().authenticated()

                )
                .csrf(AbstractHttpConfigurer::disable)
//                .cors(AbstractHttpConfigurer::disable)
                .cors(cors ->{})
                .oauth2Login(auth -> auth
//                        .loginPage("http://localhost:5173/login")
                        .defaultSuccessUrl("http://localhost:5173/",true)
                        .failureHandler(((request, response, exception) -> {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"unauthorized");
                        }))
                )

                .exceptionHandling(exception -> exception.authenticationEntryPoint(
                        ((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"unauthorized"))
                ))
                .logout(logout ->
                        logout.logoutSuccessUrl("/").permitAll());
        return http.build();

    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                WebMvcConfigurer.super.addCorsMappings(registry);
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:5173")
                        .allowedMethods("GET","POST","PUT","UPDATE","DELETE","OPTIONS")
                        .allowCredentials(true);

            }
        };
    }

}
