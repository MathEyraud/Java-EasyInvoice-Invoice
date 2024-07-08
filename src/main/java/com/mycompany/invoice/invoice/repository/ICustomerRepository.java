package com.mycompany.invoice.invoice.repository;

import com.mycompany.invoice.core.entity.customer.Customer;
import org.springframework.data.repository.CrudRepository;

public interface ICustomerRepository extends CrudRepository<Customer, Long> {
}