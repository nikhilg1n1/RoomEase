package com.roomeaseauth.Config;

import com.roomeaseauth.Controller.Oauth2LoginSuccessHandler;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.swing.text.AbstractDocument;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
    private  final Oauth2LoginSuccessHandler oauth2LoginSuccessHandler;

    public SecurityConfig(Oauth2LoginSuccessHandler oauth2LoginSuccessHandler) {
        this.oauth2LoginSuccessHandler = oauth2LoginSuccessHandler;
    }

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
                .oauth2Login(oauth -> oauth.successHandler(oauth2LoginSuccessHandler)
                        .loginPage("http://localhost:5173/login")
                       .defaultSuccessUrl("http://localhost:5173/",true)
                        .failureHandler(((request, response, exception) -> {
                           response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"unauthorized");
                        })))

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))

                .exceptionHandling(exception -> exception.authenticationEntryPoint(
                        ((request, response, authException) -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"unauthorized"))
                ))
                .logout(logout ->
                        logout.logoutSuccessUrl("/")
                                .deleteCookies("JESESSIONID")
                                .invalidateHttpSession(true));
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
