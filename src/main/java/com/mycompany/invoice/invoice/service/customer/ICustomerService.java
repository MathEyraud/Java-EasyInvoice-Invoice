package com.mycompany.invoice.invoice.service.customer;

import com.mycompany.invoice.core.entity.customer.Address;
import com.mycompany.invoice.core.entity.customer.Customer;
import com.mycompany.invoice.invoice.repository.ICustomerRepository;
import reactor.core.publisher.Mono;

public interface ICustomerService {

    Mono<Customer> createCustomer(Customer customer);
    Mono<Address> createAddress(Address address);
    Mono<Customer> getCustomerById(Long customerId);
}