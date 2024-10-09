package com.ss.easy.bank.config;

import com.ss.easy.bank.exception.CustomAccessDeniedHandler;
import com.ss.easy.bank.exception.CustomBasicAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@Profile("!prod")
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
        http.sessionManagement(smc -> smc.sessionFixation(sf -> sf.changeSessionId()) // this changeSessionId will be the default
                .invalidSessionUrl("/invalidSession").maximumSessions(3).maxSessionsPreventsLogin(true));
        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());
        http.requiresChannel(rec -> rec.anyRequest().requiresInsecure()); // only HTTP protocol
        http.authorizeHttpRequests((requests) -> requests.requestMatchers("/accounts", "/balance", "/cards", "/loans").authenticated());
        http.authorizeHttpRequests((requests) -> requests.requestMatchers("/notice", "/contacts", "/create/user", "/invalidSession").permitAll());
        http.formLogin(withDefaults()); // default form login page will appear
        http.httpBasic(hbc -> hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
        http.exceptionHandling(ehc -> ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));
        return http.build();
    }

    /**
     * This bean is used to encode the password.
     * Default, Bcrypt is the passwordEncoder mechanism.
     * Through password encoder factory class we can delegate the required password encoder.
     * If you visit createDelegatingPasswordEncoder() method you will see the multiple options.
     *
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * This feature is available from spring security 6.3
     * The use of this bean is to check the given password is strong or not.
     * If password is not strong will receive error like below
     * Error: "The provided password is compromised, please change your password"
     *
     * @return
     */
    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }

}
