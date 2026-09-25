package com.mptc.training.ecommerce.order.domain.usecase;

import com.mptc.training.ecommerce.order.domain.dto.CreateOrderCommand;
import com.mptc.training.ecommerce.order.domain.dto.CreateOrderResult;
import com.mptc.training.ecommerce.order.domain.entity.Business;
import com.mptc.training.ecommerce.order.domain.entity.Order;
import com.mptc.training.ecommerce.order.domain.entity.Product;
import com.mptc.training.ecommerce.order.domain.event.OrderCreatedEvent;
import com.mptc.training.ecommerce.order.domain.exception.OrderDomainException;
import com.mptc.training.ecommerce.order.domain.mapper.OrderDomainMapper;
import com.mptc.training.ecommerce.order.domain.port.output.BusinessRepository;
import com.mptc.training.ecommerce.order.domain.port.output.CustomerRepository;
import com.mptc.training.ecommerce.order.domain.port.output.OrderRepository;
import com.mptc.training.ecommerce.order.domain.service.OrderDomainService;
import com.mptc.training.ecommerce.order.domain.valueobject.BusinessId;
import com.mptc.training.ecommerce.order.domain.valueobject.Money;
import com.mptc.training.ecommerce.order.domain.valueobject.ProductId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class CreateOrderUseCase {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final BusinessRepository businessRepository;
    private final OrderDomainService orderDomainService;
    private final OrderDomainMapper orderDomainMapper;

    public CreateOrderResult execute(CreateOrderCommand createOrderCommand) {
        log.info("executing CreateOrderUseCase: {}", createOrderCommand);

        // Validate customer
        customerRepository.findCustomer(createOrderCommand.customerId()).orElseThrow(() -> new OrderDomainException("cloud not find customer with id" + createOrderCommand.customerId()));

        List<Product> products = createOrderCommand.items().stream().map(commandOrderItem -> Product.builder().id(new ProductId(commandOrderItem.productId())).price(new Money(commandOrderItem.price())).build()).toList();

        Business business = Business.builder().id(new BusinessId(createOrderCommand.businessId())).products(products).build();

        business = businessRepository.findBusiness(business).orElseThrow(() -> new OrderDomainException("Cloud not found business" + createOrderCommand.businessId()));

        //invoke order service logic
        Order order = orderDomainMapper.createOrderCommandToOrder(createOrderCommand);


        OrderCreatedEvent orderCreatedEvent = orderDomainService.validateAndInitiateOrder(order, business);

        var sortedNames = orderCreatedEvent.getOrder().getFailureMessages().stream().filter(s -> s.length() > 3).sorted().toList();
        log.info("Order Created event: {} ,{}", orderCreatedEvent.getOrder().getId(), sortedNames);

        //save order to database

        Order savedOrder = orderRepository.saveOrder(order);

        if (savedOrder == null) {
            throw new OrderDomainException("Order Could not saved");
        }

        return new CreateOrderResult(savedOrder.getId().value());
    }

}
