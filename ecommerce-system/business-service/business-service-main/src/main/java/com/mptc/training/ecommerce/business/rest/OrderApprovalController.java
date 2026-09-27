package com.mptc.training.ecommerce.business.rest;

import com.mptc.training.ecommerce.business.domain.entity.Business;
import com.mptc.training.ecommerce.business.domain.entity.OrderApproval;
import com.mptc.training.ecommerce.business.domain.entity.OrderDetail;
import com.mptc.training.ecommerce.business.domain.entity.Product;
import com.mptc.training.ecommerce.business.domain.event.OrderApprovalEvent;
import com.mptc.training.ecommerce.business.domain.service.BusinessDomainService;
import com.mptc.training.ecommerce.business.persistence.entity.BusinessEntity;
import com.mptc.training.ecommerce.business.persistence.entity.OrderApprovalEntity;
import com.mptc.training.ecommerce.business.persistence.entity.ProductEntity;
import com.mptc.training.ecommerce.business.persistence.repository.BusinessJpaRepository;
import com.mptc.training.ecommerce.business.persistence.repository.OrderApprovalJpaRepository;
import com.mptc.training.ecommerce.business.persistence.repository.ProductJpaRepository;
import com.mptc.training.ecommerce.order.domain.valueobject.BusinessId;
import com.mptc.training.ecommerce.order.domain.valueobject.Money;
import com.mptc.training.ecommerce.order.domain.valueobject.OrderId;
import com.mptc.training.ecommerce.order.domain.valueobject.OrderStatus;
import com.mptc.training.ecommerce.order.domain.valueobject.ProductId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/order-approvals")
@RequiredArgsConstructor
public class OrderApprovalController {

    private final BusinessJpaRepository businessJpaRepository;
    private final ProductJpaRepository productJpaRepository;
    private final OrderApprovalJpaRepository orderApprovalJpaRepository;
    private final BusinessDomainService businessDomainService;

    /**
     * Temporary stand-in for the OrderPaidEvent listener.
     * An empty body approves a paid order for every seeded product with quantity 1.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OrderApprovalResponse approve(@RequestBody(required = false) OrderPaidApprovalRequest request) {
        BusinessEntity businessEntity = businessJpaRepository.findAll().stream()
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "No business has been seeded"));

        List<ProductEntity> catalog = productJpaRepository.findByBusiness_Id(businessEntity.getId());
        if (catalog.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Seeded business has no products");
        }

        List<Product> products = toDomainProducts(catalog, request);
        Money totalAmount = products.stream()
                .map(product -> product.getPrice().multiply(product.getQuantity()))
                .reduce(Money.ZERO, Money::add);

        UUID orderId = request != null && request.orderId() != null ? request.orderId() : UUID.randomUUID();
        OrderDetail orderDetail = OrderDetail.builder()
                .id(new OrderId(orderId))
                .orderStatus(OrderStatus.PAID)
                .totalAmount(totalAmount)
                .products(products)
                .build();

        Business business = Business.builder()
                .id(new BusinessId(businessEntity.getId()))
                .active(businessEntity.isActive())
                .orderDetail(orderDetail)
                .build();

        List<String> failureMessages = new ArrayList<>();
        OrderApprovalEvent event = businessDomainService.validateOrder(business, failureMessages);
        OrderApproval orderApproval = event.getOrderApproval();

        OrderApprovalEntity saved = new OrderApprovalEntity();
        saved.setId(orderApproval.getId().value());
        saved.setBusinessId(orderApproval.getBusinessId().value());
        saved.setOrderId(orderApproval.getOrderId().value());
        saved.setApprovalStatus(orderApproval.getApprovalStatus());
        orderApprovalJpaRepository.save(saved);

        log.info("Saved order approval id={} status={} orderId={}",
                saved.getId(), saved.getApprovalStatus(), saved.getOrderId());

        return new OrderApprovalResponse(
                saved.getId(),
                saved.getBusinessId(),
                saved.getOrderId(),
                saved.getApprovalStatus(),
                event.getFailureMessages()
        );
    }

    private List<Product> toDomainProducts(List<ProductEntity> catalog, OrderPaidApprovalRequest request) {
        if (request == null || request.products() == null || request.products().isEmpty()) {
            return catalog.stream()
                    .map(product -> toDomainProduct(product, 1))
                    .toList();
        }

        Map<UUID, ProductEntity> productsById = catalog.stream()
                .collect(Collectors.toMap(ProductEntity::getId, Function.identity()));

        List<Product> products = new ArrayList<>();
        for (OrderPaidApprovalRequest.ProductQuantity line : request.products()) {
            ProductEntity productEntity = productsById.get(line.productId());
            if (productEntity == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "Product not found for this business: " + line.productId());
            }
            if (line.quantity() <= 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quantity must be greater than zero");
            }
            products.add(toDomainProduct(productEntity, line.quantity()));
        }
        return products;
    }

    private Product toDomainProduct(ProductEntity productEntity, int quantity) {
        return Product.builder()
                .id(new ProductId(productEntity.getId()))
                .name(productEntity.getName())
                .price(new Money(productEntity.getPrice()))
                .quantity(quantity)
                .available(productEntity.isAvailable())
                .build();
    }
}
