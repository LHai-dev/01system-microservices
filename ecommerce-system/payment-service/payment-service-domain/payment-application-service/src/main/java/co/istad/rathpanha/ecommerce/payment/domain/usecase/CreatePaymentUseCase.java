package co.istad.rathpanha.ecommerce.payment.domain.usecase;

import co.istad.rathpanha.ecommerce.payment.domain.dto.CreatePaymentCommand;
import co.istad.rathpanha.ecommerce.payment.domain.dto.CreatePaymentResult;
import co.istad.rathpanha.ecommerce.payment.domain.entity.CreditEntry;
import co.istad.rathpanha.ecommerce.payment.domain.entity.CreditHistory;
import co.istad.rathpanha.ecommerce.payment.domain.entity.Payment;
import co.istad.rathpanha.ecommerce.payment.domain.exception.PaymentDomainException;
import co.istad.rathpanha.ecommerce.payment.domain.mapper.PaymentDomainMapper;
import co.istad.rathpanha.ecommerce.payment.domain.port.output.CreditEntityRepository;
import co.istad.rathpanha.ecommerce.payment.domain.port.output.CreditHistoryRepository;
import co.istad.rathpanha.ecommerce.payment.domain.port.output.PaymentRepository;
import co.istad.rathpanha.ecommerce.payment.domain.service.PaymentDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CreatePaymentUseCase {
  private final PaymentDomainService paymentDomainService;
  private final PaymentRepository paymentRepository;
  private final PaymentDomainMapper paymentDomainMapper;
  private final CreditEntityRepository creditEntityRepository;
  private final CreditHistoryRepository creditHistoryRepository;

  public CreatePaymentResult execute(CreatePaymentCommand createPaymentCommand) {
    log.info("executing CreatePaymentUseCase: {}", createPaymentCommand);

    //1. convert input object by map-struct
    Payment payment = paymentDomainMapper.createPaymentCommandToPayment(createPaymentCommand);

    //2. load customer credit
    CreditEntry creditEntry = creditEntityRepository.findByCustomerId(payment.getCustomerId());
    if (creditEntry == null) {
      throw new PaymentDomainException("Could not find credit entry for customer: "
              + payment.getCustomerId().value());
    }

    //3. domain logic (validate → initialize → subtract credit → COMPLETED)
    CreditHistory creditHistory = paymentDomainService.validateAndInitiatePayment(payment, creditEntry);

    //4. save
    Payment savePayment = paymentRepository.savePayment(payment);
    if(savePayment == null){
      throw  new PaymentDomainException("Could not save payment into Database");
    }
    creditEntityRepository.save(creditEntry);
    creditHistoryRepository.save(creditHistory);

    log.info("Payment {} completed for customer {}", savePayment.getId().value(),
            payment.getCustomerId().value());

    return new CreatePaymentResult(savePayment.getId().value(), savePayment.getPaymentStatus());
  }
}
