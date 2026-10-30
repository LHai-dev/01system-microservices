package com.mptc.training.ecommerce.order;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain applyFilterChain(HttpSecurity httpSecurity) {
        //1.
        httpSecurity.authorizeHttpRequests(endpoint -> endpoint.requestMatchers("/nacos/config").permitAll().requestMatchers("/nacos/config/secured").denyAll());
        //2.
        httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        //3. disable csrf generate token
        httpSecurity.csrf(AbstractHttpConfigurer::disable);

        //4. disable form login
        httpSecurity.formLogin(AbstractHttpConfigurer::disable);

        //5. security
        httpSecurity.oauth2ResourceServer(auth2 -> auth2.jwt(Customizer.withDefaults()));

        return httpSecurity.build();

    }
}
