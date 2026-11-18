package com.mptc.training.ecommerce.customer.domain.service;

import com.mptc.training.ecommerce.domain.valueobject.Email;
import com.mptc.training.ecommerce.domain.valueobject.PhoneNumber;
import com.mptc.training.ecommerce.customer.domain.entity.Customer;
import com.mptc.training.ecommerce.customer.domain.event.CustomerCreatedEvent;
import com.mptc.training.ecommerce.customer.domain.event.CustomerDeactivatedEvent;
import com.mptc.training.ecommerce.customer.domain.event.CustomerUpdatedEvent;


public interface CustomerDomainService {
    CustomerCreatedEvent validateAndInitiateCustomer(Customer customer);

    CustomerUpdatedEvent updateCustomer(Customer customer, String familyName, String givenName,
                                        Email email, PhoneNumber phoneNumber);

    CustomerDeactivatedEvent deactivateCustomer(Customer customer);
}
