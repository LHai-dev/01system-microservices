package com.mptc.training.ecommerce.order.domain.port.output;

import com.mptc.training.ecommerce.order.domain.entity.Customer;

import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Optional<Customer> findCustomer(UUID customerId);
}
