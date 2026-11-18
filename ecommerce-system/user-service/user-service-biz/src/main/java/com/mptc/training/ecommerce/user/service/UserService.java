package com.mptc.training.ecommerce.user.service;

import com.mptc.training.ecommerce.user.dto.*;

import java.util.List;

public interface UserService {
    UserResponse create(UserCreateRequest userCreateRequest);

    List<UserResponse> list(UserQueryRequest userQueryRequest);
    UserResponse getByUsername(String username);
    UserResponse getById(String id);

    List<UserResponse> getByRoleName(String Role);
    void delete(String id);
    UserResponse update(String id,UserUpdateRequest userUpdateRequest);
    //
    UserResponse replace(String id, UserReplaceRequest userReplaceRequest);

}
