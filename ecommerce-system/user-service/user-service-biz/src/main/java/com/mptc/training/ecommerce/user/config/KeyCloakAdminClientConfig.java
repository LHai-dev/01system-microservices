package com.mptc.training.ecommerce.user.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeyCloakAdminClientConfig {

    public final static String REALM_NAME = "ecommerce";

    @Bean
    public Keycloak keycloak(){
        return KeycloakBuilder.builder()
                .serverUrl("http://localhost:8888")
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId("ecommerce-admin-client")
                .clientSecret("13aF0klLqUsHgNQmTze9fph9dWQmRYL2CYW6hzYHHXLSwiRzpI0UojUYXhpMx8iMd2QasxkBljqpBhLb0vhZBo")
                .realm("ecommerce")
                .build();
    }
}
