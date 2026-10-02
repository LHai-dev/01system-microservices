package co.istad.rathpanha.ecommerce.payment.domain.dto;

import com.mptc.training.ecommerce.domain.valueobject.PaymentStatus;

import java.util.UUID;

public record CreatePaymentResult(
    UUID paymentId,
    PaymentStatus paymentStatus
) {
}
