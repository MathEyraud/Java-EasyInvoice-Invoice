package com.mycompany.invoice.invoice.service.customer;

import com.mycompany.invoice.core.entity.customer.Address;
import com.mycompany.invoice.core.entity.customer.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Service
public class CustomerService implements ICustomerService{

    /**
     * ATTRIBUTS
     */
    private WebClient.Builder webClientBuilder;

    /**
     * CONSTRUCTEUR
     */
    @Autowired
    public CustomerService(WebClient.Builder webClientBuilder){
        this.webClientBuilder = webClientBuilder;
    }

    /**
     * METHODS
     */
    @Override
    public Mono<Customer> getCustomerById(Long customerId) {
        System.out.println(" ----- CustomerServiceNumber/getCustomerById ----- ");
        return webClientBuilder.build()
                .get()
                .uri("http://customer/customer/" + customerId)
                .retrieve()                     // Récupérer uniquement les JSON
                .bodyToMono(Customer.class);    // Convertir le JSON en objet
    }

    @Override
    public Mono<Address> createAddress(Address address) {
        System.out.println(" ----- CustomerServiceNumber/createAddress ----- ");
        return webClientBuilder.build()
                .post()
                .uri("http://customer/address")
                .bodyValue(address)
                .retrieve()                 // Récupérer uniquement les JS
                .bodyToMono(Address.class); // Convertir le JSON en objet
    }

    @Override
    public Mono<Customer> createCustomer(Customer customer) {
        System.out.println(" ----- CustomerServiceNumber/createCustomer ----- ");
        return webClientBuilder.build()
                .post()
                .uri("http://customer/customer")
                .bodyValue(customer)
                .retrieve()
                .bodyToMono(Customer.class);
    }

    /**
     * GETTERS/SETTERS
     */
}