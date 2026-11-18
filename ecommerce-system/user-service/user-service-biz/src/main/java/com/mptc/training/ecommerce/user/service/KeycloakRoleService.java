package com.mptc.training.ecommerce.user.service;

import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public interface KeycloakRoleService {
    void assignRoles(String username,String roleNameWithDelimiter);

    List<UserRepresentation> getUsersByRoleName(String roleName);

}
