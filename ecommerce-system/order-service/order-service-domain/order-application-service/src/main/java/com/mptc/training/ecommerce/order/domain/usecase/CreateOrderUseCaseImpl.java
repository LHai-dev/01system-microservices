package com.mptc.training.ecommerce.order.domain.usecase;

import com.mptc.training.ecommerce.order.domain.dto.CreateOrderCommand;
import com.mptc.training.ecommerce.order.domain.dto.CreateOrderResult;
import com.mptc.training.ecommerce.order.domain.entity.Business;
import com.mptc.training.ecommerce.order.domain.entity.Customer;
import com.mptc.training.ecommerce.order.domain.exception.OrderDomainException;
import com.mptc.training.ecommerce.order.domain.port.input.CreateOrderUseCase;
import com.mptc.training.ecommerce.order.domain.port.output.BusinessRepository;
import com.mptc.training.ecommerce.order.domain.port.output.CustomerRepository;
import com.mptc.training.ecommerce.order.domain.valueobject.BusinessId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class CreateOrderUseCaseImpl implements CreateOrderUseCase {

    private final CustomerRepository customerRepository;
    private final BusinessRepository businessRepository;

    @Override
    public CreateOrderResult execute(CreateOrderCommand createOrderCommand) {
        log.info("create order Use Case. {}", createOrderCommand);

        Customer customer = customerRepository.findCustomer(createOrderCommand.customerId())
                .orElseThrow(() -> new OrderDomainException(
                        "Could not find customer with id: " + createOrderCommand.customerId()));

        Business business = businessRepository.findBusiness(new BusinessId(createOrderCommand.businessId()))
                .orElseThrow(() -> new OrderDomainException(
                        "Could not find business with id: " + createOrderCommand.businessId()));

        if (!business.isActive()) {
            throw new OrderDomainException(
                    "Business with id: " + createOrderCommand.businessId() + " is not active");
        }

        return new CreateOrderResult(UUID.randomUUID());
    }
}
