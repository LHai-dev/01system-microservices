package dtwg.mptc.ecommerce.event;

import com.mptc.training.ecommerce.domain.event.DomainEvent;
import dtwg.mptc.ecommerce.entity.Customer;

public abstract class CustomerEvent implements DomainEvent<Customer> {
    private final Customer customer;

    public CustomerEvent(Customer customer){
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

}
