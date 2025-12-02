package com.roomease.Config;

import com.roomease.Auth.JwtFilter;
import com.roomease.Controller.Oauth2LoginSuccessHandler;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private  final Oauth2LoginSuccessHandler oauth2LoginSuccessHandler;
    private final JwtFilter jwtFilter;

    public SecurityConfig(Oauth2LoginSuccessHandler oauth2LoginSuccessHandler, JwtFilter jwtFilter) {
        this.oauth2LoginSuccessHandler = oauth2LoginSuccessHandler;
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws  Exception{
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login","/","/error","/v1/users/auth/refresh","/v1/image/{id}").permitAll()
                         .anyRequest().authenticated()

                )
                .csrf(AbstractHttpConfigurer::disable)
//                .cors(AbstractHttpConfigurer::disable)
                .cors(cors ->{})
//                .oauth2Login(auth -> auth
////                        .loginPage("http://localhost:5173/login")
//                        .defaultSuccessUrl("http://localhost:5173/",true)
//                        .failureHandler(((request, response, exception) -> {
//                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"unauthorized");
//                        }))
//                )
//                .rememberMe( r -> r
//                        .key("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")
//                        .tokenValiditySeconds(15*24*60*60))
                .oauth2Login(oauth -> oauth
                        .successHandler(oauth2LoginSuccessHandler)
                        .loginPage("http://localhost:5173/login")
                        .failureHandler(((request, response, exception) -> {
                           response.sendRedirect("http://localhost:5173/error/oauth");
                        }))
//                        .defaultSuccessUrl("http://localhost:5173/",true)

                )
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response, authException) ->{
                            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

                        })
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                        })
                )
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutSuccessUrl("/")
                                .deleteCookies("refresh_token"));
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
                        .allowedHeaders("*")
                        .exposedHeaders("Set-Cookie","Authorization")
                        .allowCredentials(true);


            }
        };
    }

}
