package co.istad.rathpanha.ecommerce.payment.domain.service;

import com.mptc.training.ecommerce.domain.valueobject.PaymentStatus;
import co.istad.rathpanha.ecommerce.payment.domain.entity.CreditEntry;
import co.istad.rathpanha.ecommerce.payment.domain.entity.CreditHistory;
import co.istad.rathpanha.ecommerce.payment.domain.entity.Payment;

public interface PaymentDomainService {
  CreditHistory validateAndInitiatePayment(Payment payment, CreditEntry creditEntry);

  void updatePaymentStatus(Payment payment, PaymentStatus newPaymentStatus);
}
