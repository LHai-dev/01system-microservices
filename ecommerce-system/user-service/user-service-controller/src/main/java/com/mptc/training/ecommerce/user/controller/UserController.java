package com.mptc.training.ecommerce.user.controller;

import com.mptc.training.ecommerce.user.dto.*;
import com.mptc.training.ecommerce.user.service.UserService;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public UserResponse create(@Valid @RequestBody UserCreateRequest userCreateRequest) {
        return userService.create(userCreateRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping("/{id}")
    public UserResponse update(@PathVariable String id, @RequestBody UserUpdateRequest userUpdateRequest) {
        return userService.update(id, userUpdateRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}")
    public UserResponse replace(@PathVariable String id,
                                @Valid @RequestBody UserReplaceRequest userReplaceRequest) {
        return userService.replace(id, userReplaceRequest);
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        userService.delete(id);
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public UserResponse getById(@PathVariable String id) {
        return userService.getById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<UserResponse> list(
            @RequestParam(required = false) String userName,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String mobilePhone,
            @RequestParam(required = false) String id,

            // test
            @RequestParam(required = false) String role
    ) {

        UserQueryRequest query = UserQueryRequest.builder()
                .userName(userName)
                .gender(gender)
                .mobilePhone(mobilePhone)
                .id(id)
                .role(role)
                .build();

        return userService.list(query);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{username}")
    public UserResponse getByUserName(@PathVariable String username) {
        return userService.getByUsername(username);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{role}")
    public List<UserResponse> getByRole(@PathVariable String role) {
        return userService.getByRoleName(role);
    }

}
