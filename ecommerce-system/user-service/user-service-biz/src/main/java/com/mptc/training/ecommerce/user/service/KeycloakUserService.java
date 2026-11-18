package com.mptc.training.ecommerce.user.service;

import com.mptc.training.ecommerce.user.dto.UserQueryRequest;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public interface KeycloakUserService {

    void create(UserRepresentation userRepresentation);
    void update(UserRepresentation userRepresentation);
    void delete(String userId);
    List<UserRepresentation> list(UserQueryRequest userQueryRequest);
    UserRepresentation getById(String userId);
    UserRepresentation getByUsername(String username);
}
