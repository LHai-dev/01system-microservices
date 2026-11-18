package com.mptc.training.ecommerce.customer.client.fallback;

import com.mptc.training.ecommerce.customer.client.dto.UserResponse;
import com.mptc.training.ecommerce.customer.client.feign.UserFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class UserFeignClientFallback implements UserFeignClient {
    @Override
    public UserResponse getById(String id) {
        log.info("UserFeignClientFallback getById: {}", id);
        return null;
    }
}
