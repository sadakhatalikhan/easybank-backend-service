package com.ss.easy.bank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {

    /**
     * This method is used to override the default spring security configuration.
     * We can copy the sample defaultSecurityFilterChain method from the SpringBootWebSecurityConfiguration.
     * override the method as per the requirement.
     *
     * @param http http security object
     * @return security filter chain
     * @throws Exception exception
     */
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        // http.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll());  this will allow all the requests
        // http.authorizeHttpRequests((requests) -> requests.anyRequest().denyAll()); this will deny all the requests
        http.authorizeHttpRequests((requests) -> requests.requestMatchers("/accounts","/balance","/cards","/loans").authenticated());
        http.authorizeHttpRequests((requests) -> requests.requestMatchers("/notice","/contacts").permitAll());
        http.formLogin(withDefaults()); // default form login page will appear
        http.httpBasic(withDefaults()); // default basic http type will be used
        // using below code we can disable the formLogin and httpBasic
        // http.formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.disable());
        // http.httpBasic(httpSecurityHttpBasicConfigurer -> httpSecurityHttpBasicConfigurer.disable());
        return http.build();
    }

}
