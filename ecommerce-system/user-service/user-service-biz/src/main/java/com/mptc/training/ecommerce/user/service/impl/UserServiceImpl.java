package com.mptc.training.ecommerce.user.service.impl;

import com.mptc.training.ecommerce.user.dto.*;
import com.mptc.training.ecommerce.user.entity.UserEntity;
import com.mptc.training.ecommerce.user.exception.PasswordMismatchException;
import com.mptc.training.ecommerce.user.exception.UserNotFoundException;
import com.mptc.training.ecommerce.user.exception.UsernameConflictException;
import com.mptc.training.ecommerce.user.mapper.UserMapper;
import com.mptc.training.ecommerce.user.repository.UserRepository;
import com.mptc.training.ecommerce.user.service.KeycloakRoleService;
import com.mptc.training.ecommerce.user.service.KeycloakUserService;
import com.mptc.training.ecommerce.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    private final KeycloakUserService keycloakUserService;
    private final KeycloakRoleService keycloakRoleService;

    @Override
    public UserResponse create(UserCreateRequest userCreateRequest) {
        UserEntity userEntity = userMapper.toEntity(userCreateRequest);

        // Validate username
        if (userRepository.existsByUsername(userCreateRequest.username())) {
            throw new UsernameConflictException(HttpStatus.CONFLICT,
                    "Username already exists");
        }

        // Validate password
        if (!userCreateRequest.password().equals(userCreateRequest.confirmedPassword())) {
            throw new PasswordMismatchException(HttpStatus.BAD_REQUEST,
                    "Password does not match");
        }

        // Save user into Keycloak
        // After saved user, save user into DB
        UserRepresentation userRepresentation = userMapper.toRepresentation(userCreateRequest);
        userRepresentation.setEnabled(true);
        userRepresentation.setEmailVerified(false);

        CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
        credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
        credentialRepresentation.setValue(userCreateRequest.password());

        userRepresentation.setCredentials(List.of(credentialRepresentation));

        keycloakUserService.create(userRepresentation);
        UserRepresentation createdUser = keycloakUserService.getByUsername(
                userEntity.getUsername()
        );
        log.info("User created: {}", createdUser);
        keycloakRoleService.assignRoles(createdUser.getId(), userCreateRequest.roleName());

        // Saving user into database
        userEntity.setId(createdUser.getId());
        userRepository.save(userEntity);

        return userMapper.toResponse(userEntity, createdUser);
    }

    @Override
    public List<UserResponse> list(UserQueryRequest userQueryRequest) {
        UserEntity userQuery = new UserEntity();
        userQuery.setId(userQueryRequest.id());
        userQuery.setUsername(userQueryRequest.userName());
        userQuery.setGender(userQueryRequest.gender());
        userQuery.setMobilePhone(userQueryRequest.mobilePhone());

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues();

        Example<UserEntity> query = Example.of(userQuery, matcher);

        return userRepository.findAll(query)
                .stream()
                .map(userEntity -> userMapper.toResponse(
                        userEntity,
                        keycloakUserService.getById(userEntity.getId())
                ))
                .toList();
    }

    @Override
    @Transactional
    public UserResponse update(String id, UserUpdateRequest userUpdateRequest) {

        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND, "User not found"));

        userMapper.toEntity(userUpdateRequest, userEntity);
        userRepository.save(userEntity);

        UserRepresentation userRepresentation = keycloakUserService.getById(id);
        if (userUpdateRequest.familyName() != null) {
            userRepresentation.setFirstName(userUpdateRequest.familyName());
        }
        if (userUpdateRequest.giveName() != null) {
            userRepresentation.setLastName(userUpdateRequest.giveName());
        }
        keycloakUserService.update(userRepresentation);

        return userMapper.toResponse(userEntity, userRepresentation);
    }

    @Override
    @Transactional
    public UserResponse replace(String id, UserReplaceRequest userReplaceRequest) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND, "User not found"));

        // Username is unique, so only check when it actually changes
        if (!userEntity.getUsername().equals(userReplaceRequest.username())
                && userRepository.existsByUsername(userReplaceRequest.username())) {
            throw new UsernameConflictException(HttpStatus.CONFLICT,
                    "Username already exists");
        }

        // for update all
        userMapper.toEntity(userReplaceRequest, userEntity);
        userRepository.save(userEntity);

        UserRepresentation userRepresentation = keycloakUserService.getById(id);
        userMapper.toRepresentation(userReplaceRequest, userRepresentation);
        keycloakUserService.update(userRepresentation);

        return userMapper.toResponse(userEntity, userRepresentation);
    }

    @Override
    public UserResponse getByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND, "User not found"));

        UserRepresentation keycloakUser = keycloakUserService.getById(userEntity.getId());

        return userMapper.toResponse(userEntity, keycloakUser);
    }

    @Override
    public UserResponse getById(String id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND, "User not found"));
        return userMapper.toResponse(userEntity, keycloakUserService.getById(userEntity.getId()));
    }


    @Override
    public List<UserResponse> getByRoleName(String Role) {

        Map<String, UserRepresentation> representationById = keycloakRoleService.getUsersByRoleName(Role)
                .stream()
                .collect(Collectors.toMap(UserRepresentation::getId, Function.identity()));

        return userRepository.findAllById(representationById.keySet())
                .stream()
                .map(userEntity -> userMapper.toResponse(userEntity, representationById.get(userEntity.getId())))
                .toList();
    }

    @Override
    @Transactional
    public void delete(String id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(HttpStatus.NOT_FOUND, "User not found"));
        userRepository.delete(userEntity);
        keycloakUserService.delete(id);
    }

}
