package co.istad.rathpanha.ecommerce.payment.persistence.repository;

import co.istad.rathpanha.ecommerce.payment.persistence.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentJpaRepository extends JpaRepository<PaymentEntity, UUID> {
}
