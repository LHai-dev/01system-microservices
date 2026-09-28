package com.mptc.training.ecommerce.business.domain.usecase;

import com.mptc.training.ecommerce.business.domain.dto.OrderApprovalCommand;
import com.mptc.training.ecommerce.business.domain.exception.BusinessDomainException;
import com.mptc.training.ecommerce.business.domain.port.output.BusinessRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class CreateOrderApprovalUseCase {

    private final BusinessRepository businessRepository;

    public CreateOrderApprovalResult execute(OrderApprovalCommand orderApprovalCommand) {
        log.info("executing CreateOrderApprovalUseCase: {}", orderApprovalCommand);

        // validate business
        businessRepository.findBusiness(orderApprovalCommand.businessId())
                .orElseThrow(() -> new BusinessDomainException("Business not found"));



    }

}
