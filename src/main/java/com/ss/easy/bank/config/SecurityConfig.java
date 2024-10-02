package com.ss.easy.bank.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import javax.sql.DataSource;

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
        http.csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable());
        http.authorizeHttpRequests((requests) -> requests.requestMatchers("/accounts", "/balance", "/cards", "/loans").authenticated());
        http.authorizeHttpRequests((requests) -> requests.requestMatchers("/notice", "/contacts", "/create/user").permitAll());
        http.formLogin(withDefaults()); // default form login page will appear
        http.httpBasic(withDefaults()); // default basic http type will be used
        // using below code we can disable the formLogin and httpBasic
        // http.formLogin(httpSecurityFormLoginConfigurer -> httpSecurityFormLoginConfigurer.disable());
        // http.httpBasic(httpSecurityHttpBasicConfigurer -> httpSecurityHttpBasicConfigurer.disable());
        return http.build();
    }

    /**
     * This method is an example of InMemoryUserDetailsManager.
     * Using this we can configure multiple users to access our protected resources.
     * This is not recommended for production application.
     * <p>
     * Note: Before restart the application remove below two properties from application.properties file.
     * 1. spring.security.user.name
     * 2. spring.security.user.password
     * <p>
     * In password I have added the noop to bypass the password encoding.
     *
     * @return
     */
   /* @Bean
    public UserDetailsService userDetailsService() {
        // Below two commented line for the plain password
        // UserDetails user = User.withUsername("user").password("{noop}12345").authorities("read").build();
        // UserDetails admin = User.withUsername("admin").password("{noop}67890").authorities("read").build();

        // Below is an example for the Bcrypt password encoder
        UserDetails user = User.withUsername("user")
                .password("{bcrypt}$2a$12$sKqMhK5enr6Yz8k/0JexBO7Bk/tGTDStr3/fyWpnAX5DzbpEJeE12") // easybank@12345
                .authorities("read")
                .build();
        UserDetails admin = User.withUsername("admin")
                .password("{bcrypt}$2a$12$k9p.uYaZP2LKWyVLCNBLXutjWeO34l8uqEPU3T0JhQkPWKqkoiTPy") // easybank@67890
                .authorities("admin")
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }*/

    /**
     * This bean is only for the JDBC user details manager.
     * If we're using customer user details validation then we need to comment this bean.
     *
     * @param source
     * @return
     */
    /*@Bean
    public UserDetailsService userDetailsService(DataSource source){
        return new JdbcUserDetailsManager(source);
    }*/

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
