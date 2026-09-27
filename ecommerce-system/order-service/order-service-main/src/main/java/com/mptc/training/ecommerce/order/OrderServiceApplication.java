package com.mptc.training.ecommerce.order;

import com.mptc.training.ecommerce.order.persistence.entity.BusinessEntity;
import com.mptc.training.ecommerce.order.persistence.entity.CustomerEntity;
import com.mptc.training.ecommerce.order.persistence.repository.BusinessJpaRepository;
import com.mptc.training.ecommerce.order.persistence.repository.CustomerJpaRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@EntityScan(basePackages = {"com.mptc.training.ecommerce.order.persistence"})
@EnableJpaRepositories(basePackages = {"com.mptc.training.ecommerce.order.persistence"})
@SpringBootApplication
public class OrderServiceApplication {

    static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner seedDatabase(CustomerJpaRepository customerRepository, BusinessJpaRepository businessRepository) {
        return args -> {
            CustomerEntity customer = new CustomerEntity();
            customer.setId(UUID.randomUUID());
            customer.setFamilyName("John");
            customer.setGivenName("Cena");
            customer.setUsername("johncena");
            customerRepository.save(customer);

            BusinessEntity business = new BusinessEntity();
            business.setBusinessId(UUID.randomUUID());
            business.setProductId(UUID.randomUUID());
            business.setBusinessActive(true);
            business.setProductName("iPhone 18 Pro Max");
            business.setProductPrice(new BigDecimal("2000"));
            businessRepository.save(business);

            log.info("Seeded customerId={}, businessId={}, productId={}", customer.getId(), business.getBusinessId(), business.getProductId());
        };
    }
}
