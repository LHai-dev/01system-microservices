package co.istad.rathpanha.ecommerce.payment.persistence.adapter;

import co.istad.rathpanha.ecommerce.payment.domain.entity.CreditHistory;
import co.istad.rathpanha.ecommerce.payment.domain.port.output.CreditHistoryRepository;
import co.istad.rathpanha.ecommerce.payment.persistence.entity.CreditHistoryEntity;
import co.istad.rathpanha.ecommerce.payment.persistence.mapper.CreditHistoryPersistenceMapper;
import co.istad.rathpanha.ecommerce.payment.persistence.repository.CreditHistoryJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CreditHistoryRepositoryAdapter implements CreditHistoryRepository {
  private final CreditHistoryJpaRepository creditHistoryJpaRepository;
  private final CreditHistoryPersistenceMapper creditHistoryPersistenceMapper;

  @Override
  public CreditHistory save(CreditHistory creditHistory) {
    CreditHistoryEntity creditHistoryEntity =
        creditHistoryPersistenceMapper.creditHistoryToCreditHistoryEntity(creditHistory);
    CreditHistoryEntity savedCreditHistoryEntity = creditHistoryJpaRepository.save(creditHistoryEntity);
    return creditHistoryPersistenceMapper.creditHistoryEntityToCreditHistory(savedCreditHistoryEntity);
  }
}
