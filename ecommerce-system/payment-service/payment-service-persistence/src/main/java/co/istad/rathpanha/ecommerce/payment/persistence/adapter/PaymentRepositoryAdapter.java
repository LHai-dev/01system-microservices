package co.istad.rathpanha.ecommerce.payment.persistence.adapter;

import co.istad.rathpanha.ecommerce.payment.domain.entity.Payment;
import co.istad.rathpanha.ecommerce.payment.domain.port.output.PaymentRepository;
import co.istad.rathpanha.ecommerce.payment.persistence.entity.PaymentEntity;
import co.istad.rathpanha.ecommerce.payment.persistence.mapper.PaymentPersistenceMapper;
import co.istad.rathpanha.ecommerce.payment.persistence.repository.PaymentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryAdapter implements PaymentRepository {
  private final PaymentJpaRepository paymentJpaRepository;
  private final PaymentPersistenceMapper paymentPersistenceMapper;

  @Override
  public Payment savePayment(Payment payment) {
    PaymentEntity paymentEntity = paymentPersistenceMapper.paymentToPaymentEntity(payment);
    PaymentEntity savedPaymentEntity = paymentJpaRepository.save(paymentEntity);
    return paymentPersistenceMapper.paymentEntityToPayment(savedPaymentEntity);
  }
}
