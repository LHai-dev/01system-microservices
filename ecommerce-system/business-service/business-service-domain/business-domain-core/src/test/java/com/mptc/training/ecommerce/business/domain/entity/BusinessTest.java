package com.mptc.training.ecommerce.business.domain.entity;

import com.mptc.training.ecommerce.order.domain.valueobject.BusinessId;
import com.mptc.training.ecommerce.order.domain.valueobject.Money;
import com.mptc.training.ecommerce.order.domain.valueobject.OrderId;
import com.mptc.training.ecommerce.order.domain.valueobject.OrderStatus;
import com.mptc.training.ecommerce.order.domain.valueobject.ProductId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BusinessTest {

    @Test
    void shouldValidatePaidOrderWhenProductsAreAvailableAndTotalMatches() {
        List<String> failureMessages = new ArrayList<>();

        business(OrderStatus.PAID, true, "20.00").validateOrder(failureMessages);

        assertTrue(failureMessages.isEmpty());
    }

    @Test
    void shouldAddFailureMessageWhenOrderIsNotPaid() {
        List<String> failureMessages = new ArrayList<>();

        business(OrderStatus.PENDING, true, "20.00").validateOrder(failureMessages);

        assertEquals(List.of("Order is not paid"), failureMessages);
    }

    @Test
    void shouldAddFailureMessageWhenProductIsUnavailable() {
        List<String> failureMessages = new ArrayList<>();

        business(OrderStatus.PAID, false, "20.00").validateOrder(failureMessages);

        assertEquals(1, failureMessages.size());
        assertTrue(failureMessages.getFirst().contains("is not available"));
    }

    @Test
    void shouldAddFailureMessageWhenTotalDoesNotMatch() {
        List<String> failureMessages = new ArrayList<>();

        business(OrderStatus.PAID, true, "19.99").validateOrder(failureMessages);

        assertEquals(List.of("Order total amount does not match product total"), failureMessages);
    }

    private Business business(OrderStatus orderStatus, boolean productAvailable, String totalAmount) {
        Product product = Product.builder()
                .id(new ProductId(UUID.randomUUID()))
                .name("Test product")
                .price(new Money(new BigDecimal("10.00")))
                .quantity(2)
                .available(productAvailable)
                .build();

        OrderDetail orderDetail = OrderDetail.builder()
                .id(new OrderId(UUID.randomUUID()))
                .orderStatus(orderStatus)
                .totalAmount(new Money(new BigDecimal(totalAmount)))
                .products(List.of(product))
                .build();

        return Business.builder()
                .id(new BusinessId(UUID.randomUUID()))
                .active(true)
                .orderDetail(orderDetail)
                .build();
    }
}
