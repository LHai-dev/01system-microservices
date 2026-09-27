package com.mptc.training.ecommerce.business;

import com.mptc.training.ecommerce.business.persistence.entity.BusinessEntity;
import com.mptc.training.ecommerce.business.persistence.entity.ProductEntity;
import com.mptc.training.ecommerce.business.persistence.repository.BusinessJpaRepository;
import com.mptc.training.ecommerce.business.persistence.repository.ProductJpaRepository;
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
@EntityScan(basePackages = {"com.mptc.training.ecommerce.business.persistence"})
@EnableJpaRepositories(basePackages = {"com.mptc.training.ecommerce.business.persistence"})
@SpringBootApplication
public class BusinessServiceApplication {

    static void main(String[] args) {
        SpringApplication.run(BusinessServiceApplication.class, args);
    }

    @Bean
    CommandLineRunner seedDatabase(BusinessJpaRepository businessRepository, ProductJpaRepository productRepository) {
        return args -> {
            BusinessEntity business = new BusinessEntity();
            business.setId(UUID.randomUUID());
            business.setActive(true);
            businessRepository.save(business);

            ProductEntity firstProduct = new ProductEntity();
            firstProduct.setId(UUID.randomUUID());
            firstProduct.setName("iPhone 18 Pro Max");
            firstProduct.setPrice(new BigDecimal("2000"));
            firstProduct.setAvailable(true);
            firstProduct.setBusiness(business);

            ProductEntity secondProduct = new ProductEntity();
            secondProduct.setId(UUID.randomUUID());
            secondProduct.setName("AirPods Pro");
            secondProduct.setPrice(new BigDecimal("250"));
            secondProduct.setAvailable(true);
            secondProduct.setBusiness(business);

            productRepository.save(firstProduct);
            productRepository.save(secondProduct);

            log.info("Seeded businessId={}, productIds={}, {}",
                    business.getId(), firstProduct.getId(), secondProduct.getId());
        };
    }
}
