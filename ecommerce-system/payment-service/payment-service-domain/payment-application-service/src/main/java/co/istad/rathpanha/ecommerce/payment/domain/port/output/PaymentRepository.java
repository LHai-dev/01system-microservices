package co.istad.rathpanha.ecommerce.payment.domain.port.output;

import co.istad.rathpanha.ecommerce.payment.domain.entity.Payment;

public interface PaymentRepository {
  Payment savePayment(Payment payment);
}
