package com.mptc.training.ecommerce.customer.restapi.controller;


import com.mptc.training.ecommerce.customer.client.dto.UserResponse;
import com.mptc.training.ecommerce.customer.client.feign.UserFeignClient;
import com.mptc.training.ecommerce.customer.restapi.dto.CustomerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerQueryController {
    private final UserFeignClient userFeignClient;

    @GetMapping("/check-user/{userId}")
    public UserResponse checkUser(@PathVariable String userId) {
        return userFeignClient.getById(userId);
    }

//    @GetMapping
//    public CustomerResponse getAll(
//            @RequestParam(required = false, defaultValue = "0")
//            @RequestParam(required = false, defaultValue = "10")
//            ) {
//
//    }

}