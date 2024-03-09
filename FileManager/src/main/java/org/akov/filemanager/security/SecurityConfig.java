package org.akov.filemanager.security;

import org.akov.filemanager.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig  {


    @Bean
    public UserDetailsService userDetailsService() {
        return new UserService();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }



    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        // cette méthode elle va nous permettre de s'identifier avec la page login
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }


    @Bean
    public SerurityFilter serurityFilter() {
        return new SerurityFilter();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception{

        httpSecurity.csrf(crs -> crs.disable());
        httpSecurity.authorizeHttpRequests(auth -> auth
                        //.requestMatchers("/api/**").hasAnyAuthority( "ADMIN")
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/dashboard/**").hasAnyAuthority( "ADMIN")
                        .requestMatchers("/user/**").hasAnyAuthority( "USER")

                        .anyRequest().permitAll()
                );
        httpSecurity.formLogin((form) -> form
                        .loginPage("/login")
                        .successHandler(new LoginSuccessHandler())
                        .permitAll()
                );



                //.addFilterBefore(serurityFilter(), UsernamePasswordAuthenticationFilter.class)
        return httpSecurity.build();
    }

}
