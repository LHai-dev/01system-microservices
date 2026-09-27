package com.mptc.training.ecommerce.business;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"com.mptc.training.ecommerce.business.persistence"})
@EnableJpaRepositories(basePackages = {"com.mptc.training.ecommerce.business.persistence"})
@SpringBootApplication
public class BusinessServiceApplication {

    static void main(String[] args) {
        SpringApplication.run(BusinessServiceApplication.class, args);
    }

}
