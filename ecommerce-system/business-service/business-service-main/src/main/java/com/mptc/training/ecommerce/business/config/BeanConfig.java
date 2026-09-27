package com.mptc.training.ecommerce.business.config;

import com.mptc.training.ecommerce.business.domain.service.BusinessDomainService;
import com.mptc.training.ecommerce.business.domain.service.BusinessDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public BusinessDomainService businessDomainService() {
        return new BusinessDomainServiceImpl();
    }
}
