package co.istad.rathpanha.ecommerce.payment;

import co.istad.rathpanha.ecommerce.payment.domain.service.PaymentDomainService;
import co.istad.rathpanha.ecommerce.payment.domain.service.PaymentDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {
  @Bean
  public PaymentDomainService paymentDomainService() {
    return new PaymentDomainServiceImpl();
  }
}
