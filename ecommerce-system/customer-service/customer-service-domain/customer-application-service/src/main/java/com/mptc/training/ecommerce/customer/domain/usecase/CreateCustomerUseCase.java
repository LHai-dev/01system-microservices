package com.mptc.training.ecommerce.customer.domain.usecase;

import com.mptc.training.ecommerce.customer.client.dto.UserResponse;
import com.mptc.training.ecommerce.customer.client.exception.UserServiceUnavailableException;
import com.mptc.training.ecommerce.customer.client.feign.UserFeignClient;
import com.mptc.training.ecommerce.customer.domain.dto.CreateCustomerCommand;
import com.mptc.training.ecommerce.customer.domain.dto.CreateCustomerResult;
import com.mptc.training.ecommerce.customer.domain.exception.CustomerAlreadyExistsException;
import com.mptc.training.ecommerce.customer.domain.exception.CustomerDomainException;
import com.mptc.training.ecommerce.customer.domain.mapper.CustomerDataMapper;
import com.mptc.training.ecommerce.customer.domain.port.output.CustomerRepository;
import com.mptc.training.ecommerce.customer.domain.entity.Customer;
import com.mptc.training.ecommerce.customer.domain.event.CustomerCreatedEvent;
import com.mptc.training.ecommerce.customer.domain.service.CustomerDomainService;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

// Use case: register a new customer.
// Called by the REST API controller (POST /api/v1/customers).
// Flow: check duplicates -> build domain Customer -> domain validates & initiates -> save -> return new id
@Component
@Slf4j
@RequiredArgsConstructor
public class CreateCustomerUseCase {

    private final CustomerDomainService customerDomainService; // business rules (from customer-domain-core)
    private final CustomerRepository customerRepository;
    private final CustomerDataMapper customerDataMapper;
    private final UserFeignClient userFeignClient;

    @Transactional // everything below runs in one DB transaction: if anything fails, nothing is saved
    public CreateCustomerResult execute(CreateCustomerCommand createCustomerCommand) {
        log.info("Execute CreateCustomerUseCase : {}", createCustomerCommand);

        try {
            UserResponse userResponse = userFeignClient.getById(createCustomerCommand.userId());
            log.info("User response: {}", userResponse);
        } catch (FeignException.NotFound e) {
            throw new CustomerDomainException("User with id '" + createCustomerCommand.userId() + "' not found!");
        } catch (UserServiceUnavailableException e) {
            throw new CustomerDomainException(e.getMessage());
        }

        if (customerRepository.existsByUsername(createCustomerCommand.username())) {
            throw new CustomerAlreadyExistsException("Username already exists");
        }
        if (customerRepository.existsByEmail(createCustomerCommand.email())) {
            throw new CustomerAlreadyExistsException("Email already exists");
        }

        Customer customer = customerDataMapper.createCustomerCommandToCustomer(createCustomerCommand);

        CustomerCreatedEvent customerCreatedEvent = customerDomainService.validateAndInitiateCustomer(customer);

        Customer savedCustomer = customerRepository.save(customer);

        log.info("Customer created with id: {} at {}",
                savedCustomer.getId().value(), customerCreatedEvent.getCreatedAt());

        return new CreateCustomerResult(savedCustomer.getId().value());
    }
}
