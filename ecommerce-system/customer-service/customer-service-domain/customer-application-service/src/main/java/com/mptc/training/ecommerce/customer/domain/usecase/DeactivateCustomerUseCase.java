package com.mptc.training.ecommerce.customer.domain.usecase;

import com.mptc.training.ecommerce.customer.domain.dto.DeactivateCustomerCommand;
import com.mptc.training.ecommerce.customer.domain.exception.CustomerNotFoundException;
import com.mptc.training.ecommerce.customer.domain.port.output.CustomerRepository;
import com.mptc.training.ecommerce.domain.valueobject.CustomerId;
import com.mptc.training.ecommerce.customer.domain.entity.Customer;
import com.mptc.training.ecommerce.customer.domain.event.CustomerDeactivatedEvent;
import com.mptc.training.ecommerce.customer.domain.service.CustomerDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


// Use case: deactivate a customer (status ACTIVE -> INACTIVE). The row is NOT deleted.
// Called by the REST API controller (PATCH /api/v1/customers/{customerId}/deactivate).
// Flow: load customer -> domain deactivates -> save
@Component
@Slf4j
@RequiredArgsConstructor
public class DeactivateCustomerUseCase {

    private final CustomerDomainService customerDomainService;
    private final CustomerRepository customerRepository;

    @Transactional
    public void execute(DeactivateCustomerCommand deactivateCustomerCommand) {
        log.info("Execute DeactivateCustomerUseCase : {}", deactivateCustomerCommand);

        Customer customer = customerRepository.findById(new CustomerId(deactivateCustomerCommand.customerId()))
                .orElseThrow(() -> new CustomerNotFoundException(
                        "Customer not found: " + deactivateCustomerCommand.customerId()));

        CustomerDeactivatedEvent customerDeactivatedEvent = customerDomainService.deactivateCustomer(customer);
        customerRepository.save(customer);

        log.info("Customer deactivated with id: {} at {}",
                customerDeactivatedEvent.getCustomerId().value(), customerDeactivatedEvent.getDeactivatedAt());
    }
}
