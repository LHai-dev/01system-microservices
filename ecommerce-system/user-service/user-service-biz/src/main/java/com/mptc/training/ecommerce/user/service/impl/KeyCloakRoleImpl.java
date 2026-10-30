package com.mptc.training.ecommerce.user.service.impl;

import com.mptc.training.ecommerce.user.config.KeyCloakAdminClientConfig;
import com.mptc.training.ecommerce.user.service.KeycloakRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class KeyCloakRoleImpl implements KeycloakRoleService {
    private final Keycloak keycloak;

    @Override
    public void assignRoles(String userId, String roleNameWithDelimiter) {

        String[] roleNameList = roleNameWithDelimiter.split(",");
        List<RoleRepresentation> roleRepresentationList = Arrays.stream(roleNameList)
                .map(this::getKeycloakRoleByName)
                .toList();

        keycloak.realm(KeyCloakAdminClientConfig.REALM_NAME)
                .users()
                .get(userId)
                .roles()
                .realmLevel()
                .add(roleRepresentationList);
    }


    @Override
    public List<UserRepresentation> getUsersByRoleName(String roleName) {
        return keycloak.realm(KeyCloakAdminClientConfig.REALM_NAME)
                .roles()
                .get(roleName)
                .getUserMembers();
    }

    private RoleRepresentation getKeycloakRoleByName(String roleName) {
        return keycloak.realm(KeyCloakAdminClientConfig.REALM_NAME)
                .roles()
                .get(roleName)
                .toRepresentation();
    }
}
