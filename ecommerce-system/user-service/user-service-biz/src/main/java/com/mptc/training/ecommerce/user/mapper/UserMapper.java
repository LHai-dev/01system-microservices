package com.mptc.training.ecommerce.user.mapper;

import com.mptc.training.ecommerce.user.dto.UserCreateRequest;
import com.mptc.training.ecommerce.user.dto.UserReplaceRequest;
import com.mptc.training.ecommerce.user.dto.UserResponse;
import com.mptc.training.ecommerce.user.dto.UserUpdateRequest;
import com.mptc.training.ecommerce.user.entity.UserEntity;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.*;


@Mapper(componentModel = "spring")
public interface UserMapper {

    default UserResponse toResponse(UserEntity userEntity, UserRepresentation userRepresentation) {
        return UserResponse.builder()
                .id(userEntity.getId())
                .familyName(userRepresentation.getFirstName())
                .givenName(userRepresentation.getLastName())
                .address(userEntity.getAddress())
                .birthday(userEntity.getBirthday())
                .gender(userEntity.getGender())
                .mobilePhone(userEntity.getMobilePhone())
                .build();
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void toEntity(UserUpdateRequest userUpdateRequest, @MappingTarget UserEntity userEntity);

    UserEntity toEntity(UserCreateRequest userCreateRequest);

    UserResponse toResponse(UserEntity userEntity);

    @Mapping(source = "familyName", target = "firstName")
    @Mapping(source = "givenName", target = "lastName")
    UserRepresentation toRepresentation(UserCreateRequest userCreateRequest);

    // for replace all data
    void toEntity(UserReplaceRequest userReplaceRequest, @MappingTarget UserEntity userEntity);

    @Mapping(source = "familyName", target = "firstName")
    @Mapping(source = "givenName", target = "lastName")
    void toRepresentation(UserReplaceRequest userReplaceRequest, @MappingTarget UserRepresentation userRepresentation);

}