package co.istad.rathpanha.ecommerce.payment.domain.port.output;

import co.istad.rathpanha.ecommerce.payment.domain.entity.CreditEntry;
import com.mptc.training.ecommerce.domain.valueobject.CustomerId;

public interface CreditEntityRepository {
  CreditEntry findByCustomerId(CustomerId customerId);

  CreditEntry save(CreditEntry creditEntry);
}
