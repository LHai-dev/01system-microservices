package com.mptc.training.ecommerce.user.service.impl;

import com.mptc.training.ecommerce.user.config.KeyCloakAdminClientConfig;
import com.mptc.training.ecommerce.user.dto.UserQueryRequest;
import com.mptc.training.ecommerce.user.exception.KeyCloakException;
import com.mptc.training.ecommerce.user.service.KeycloakUserService;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class KeycloakUserServiceImpl implements KeycloakUserService {


    private final Keycloak keycloak;

    @Override
    public void create(UserRepresentation userRepresentation) {
        try (Response response = keycloak.realm(KeyCloakAdminClientConfig.REALM_NAME)
                .users()
                .create(userRepresentation)) {
            if (response.getStatus() != HttpStatus.CREATED.value()) {
                throw new KeyCloakException(
                        HttpStatus.valueOf(response.getStatus()),
                        "Create user in keycloak failed with status " + response.getStatus()
                );
            }
        }
    }

    @Override
    public void update(UserRepresentation userRepresentation) {
        keycloak.realm(KeyCloakAdminClientConfig.REALM_NAME)
                .users()
                .get(userRepresentation.getId())
                .update(userRepresentation);
    }

    @Override
    public void delete(String userId) {
        try (Response response = keycloak.realm(KeyCloakAdminClientConfig.REALM_NAME)
                .users()
                .delete(userId)) {
            if (response.getStatus() != HttpStatus.NO_CONTENT.value()) {
                throw new KeyCloakException(
                        HttpStatus.valueOf(response.getStatus()),
                        "Delete user in keycloak failed with status " + response.getStatus()
                );
            }
        }
    }

    @Override
    public List<UserRepresentation> list(UserQueryRequest userQueryRequest) {

        return keycloak.realm(KeyCloakAdminClientConfig.REALM_NAME)
                .users()
//                .search(userQueryRequest);
                .search(userQueryRequest.userName());
    }

    @Override
    public UserRepresentation getById(String userId) {
        try {
            return keycloak.realm(KeyCloakAdminClientConfig.REALM_NAME)
                    .users()
                    .get(userId)
                    .toRepresentation();
        } catch (NotFoundException e) {
            throw new KeyCloakException(HttpStatus.NOT_FOUND, "User in Keycloak not found");
        }
    }

    @Override
    public UserRepresentation getByUsername(String username) {
        return keycloak.realm(KeyCloakAdminClientConfig.REALM_NAME)
                .users()
                .search(username, true)
                .stream()
                .findFirst()
                .orElseThrow(() -> new KeyCloakException(
                        HttpStatus.NOT_FOUND,
                        "User in Keycloak not found"
                ));
    }

}