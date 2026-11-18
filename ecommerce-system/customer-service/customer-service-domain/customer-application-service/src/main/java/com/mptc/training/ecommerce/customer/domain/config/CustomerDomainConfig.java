package com.mptc.training.ecommerce.customer.domain.config;

import com.mptc.training.ecommerce.customer.domain.service.CustomerDomainService;
import com.mptc.training.ecommerce.customer.domain.service.CustomerDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// customer-domain-core has no Spring annotations, so we register its service as a bean here
@Configuration
public class CustomerDomainConfig {

    @Bean
    public CustomerDomainService customerDomainService() {
        return new CustomerDomainServiceImpl();
    }
}
