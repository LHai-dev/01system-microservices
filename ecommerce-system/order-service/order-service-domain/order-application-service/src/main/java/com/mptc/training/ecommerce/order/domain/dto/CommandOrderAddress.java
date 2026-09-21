package com.mptc.training.ecommerce.order.domain.dto;


public record CommandOrderAddress(
        String street,
        String postCode,
        String city) {

}
