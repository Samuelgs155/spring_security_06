package com.spring.security.app_security.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.util.List;

@Configuration
// @EnableMethodSecurity
public class SecurityConfig {

    //@Autowired
    //CsrfCookieFilter csrfCookieFilter;


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        var requestHandler = new CsrfTokenRequestAttributeHandler();
        requestHandler.setCsrfRequestAttributeName("_csrf");

        http.authorizeHttpRequests(auth ->
                    /*
                        auth.anyRequest()
                        // .authenticated()
                        .permitAll()
                     */
                    auth
                            // .requestMatchers("/loans/**", "/balance/**","/accounts/**","/cards/**")
                            .requestMatchers("/loans/**","/balance/**").hasRole("USER")
                            .requestMatchers("/cards/**","/accounts/**").hasRole("ADMIN")
                            // .authenticated()
                            .requestMatchers("/welcome","/about_us").permitAll()
                            .anyRequest().permitAll()
                    )
                    .formLogin(Customizer.withDefaults())
                    .httpBasic(Customizer.withDefaults());
        // http.cors(AbstractHttpConfigurer::disable);
        // http.csrf(AbstractHttpConfigurer::disable);
        http.cors(cors -> corsConfigurationSource());
        http.csrf(csrf ->
                    csrf.csrfTokenRequestHandler(requestHandler)
                        .ignoringRequestMatchers("/welcome","/about_us")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                )
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class);

        return http.build();
    }

    /*
    @Bean
    InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        var admin = User.withUsername("admin").password("to_be_encoded").roles("ADMIN").build();
        var user = User.withUsername("user").password("to_be_encoded").roles("USER").build();
        return new InMemoryUserDetailsManager(admin, user);
    }
    */

    /*
    @Bean
    UserDetailsService uSerDetailsService(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }
    */



    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

/*
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
 */

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        var config = new CorsConfiguration();
        //config.setAllowedOrigins(List.of("http://localhost:4200","http://my-app.com"));
        config.setAllowedOrigins(List.of("*"));
        // config.setAllowedMethods(List.of("GET","POST","PUT","DELETE"));
        config.setAllowedMethods(List.of("*"));
        config.setAllowedMethods(List.of("+"));

        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}
