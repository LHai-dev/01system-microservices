package co.istad.rathpanha.ecommerce.payment.domain.port.output;

import co.istad.rathpanha.ecommerce.payment.domain.entity.CreditHistory;

public interface CreditHistoryRepository {
  CreditHistory save(CreditHistory creditHistory);
}
