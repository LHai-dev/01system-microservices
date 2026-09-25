package com.mptc.training.ecommerce.order;


import com.mptc.training.ecommerce.order.domain.service.OrderDomainService;
import com.mptc.training.ecommerce.order.domain.service.OrderDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//how to config bean
// 1. annotation base
// 2. java base
@Configuration
public class BeanConfiguration {

    @Bean
    public OrderDomainService orderDomainService() {
        return new OrderDomainServiceImpl();
    }

}
