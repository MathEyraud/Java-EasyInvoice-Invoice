package com.mycompany.invoice.invoice.api;

import com.mycompany.invoice.core.entity.customer.Address;
import com.mycompany.invoice.core.entity.customer.Customer;
import com.mycompany.invoice.core.entity.invoice.Invoice;
import com.mycompany.invoice.invoice.service.invoice.IInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/invoice")
public class InvoiceRessource {

    /**
     * ATTRIBUTS
     */
    private IInvoiceService     invoiceService;
    private WebClient.Builder   webClientBuilder;

    /**
     * CONSTRUCTEUR
     */
    @Autowired
    public InvoiceRessource(
            IInvoiceService invoiceService,
            WebClient.Builder webClientBuilder
    ){
        this.invoiceService     = invoiceService;
        this.webClientBuilder   = webClientBuilder;
    }

    /**
     * METHODS
     */
    // Methode avec RestTemplate
    /*@GetMapping
    public Iterable<Invoice> getAllInvoices(){
        System.out.println(" ----- InvoiceRessource/getAllInvoices ----- ");

        // Récupérer les données depuis le service
        Iterable<Invoice> listInvoice = invoiceService.getListInvoice();

        // Récupérer les customers d'un autre micro service
        listInvoice.forEach(invoice -> {
            invoice.setCustomer(
                    restTemplate.getForObject(
                            "http://customer/customer/"+invoice.getIdCustomer(),
                            Customer.class
                    )
            );
        });

        // Retourner les données
        return listInvoice;
    }*/
    // Méthode avec WebClient
    @GetMapping
    public Iterable<Invoice> getAllInvoices() {
        System.out.println(" ----- InvoiceRessource/getAllInvoices ----- ");

        // Récupérer les données depuis le service
        Iterable<Invoice> listInvoice = invoiceService.getListInvoice();

        // Récupérer les customers d'un autre micro service
        listInvoice.forEach(invoice -> {

            // Utiliser WebClient pour récupérer les customers
            Mono<Customer> customerMono = webClientBuilder.build()
                    .get()
                    .uri("http://customer/customer/" + invoice.getIdCustomer())
                    .retrieve()                     // Récupérer uniquement les JSON
                    .bodyToMono(Customer.class);    // Convertir le JSON en objet

            // Bloquer pour obtenir le résultat (en attendant une méthode réactive complète)
            Customer customer = customerMono.block();
            invoice.setCustomer(customer);
        });

        // Retourner les données
        return listInvoice;
    }

    // Methode avec RestTemplate
    /*@GetMapping("/{id}")
    public Invoice getInvoiceById(@PathVariable("id") String number){
        System.out.println(" ----- InvoiceRessource/getInvoiceById ----- ");

        // Récupérer les données depuis le service
        Invoice invoice = invoiceService.getInvoiceByNumber(number);

        // Récupérer le customer depuis un autre micro service
        final Customer customer = restTemplate.getForObject(
                "http://customer/customer/"+invoice.getIdCustomer(),
                Customer.class
        );

        // Récupérer l'adresses depuis un autre micro service
        // A partir du customer récupéré
        final Address address = restTemplate.getForObject(
                "http://localhost:8081/address/" + customer.getAddress().getId(),
                Address.class
        );

        customer.setAddress(address);
        invoice.setCustomer(customer);

        // Retourner les données
        return invoice;
    }*/

    // Méthode avec WebClient
    @GetMapping("/{id}")
    public Invoice getInvoiceById(@PathVariable("id") String number) {
        System.out.println(" ----- InvoiceRessource/getInvoiceById ----- ");

        // Récupérer les données depuis le service
        Invoice invoice = invoiceService.getInvoiceByNumber(number);

        // Utiliser WebClient pour récupérer le customer
        Mono<Customer> customerMono = webClientBuilder.build()
                .get()
                .uri("http://customer/customer/" + invoice.getIdCustomer())
                .retrieve()
                .bodyToMono(Customer.class);

        // Bloquer pour obtenir le résultat (en attendant une méthode réactive complète)
        Customer customer = customerMono.block();

        // Utiliser WebClient pour récupérer l'address
        Mono<Address> addressMono = webClientBuilder.build()
                .get()
                .uri("http://customer/address/" + customer.getIdAddress())
                .retrieve()
                .bodyToMono(Address.class);

        // Bloquer pour obtenir le résultat (en attendant une méthode réactive complète)
        Address address = addressMono.block();

        customer.setAddress(address);
        invoice.setCustomer(customer);

        return invoice;
    }

    @PostMapping
    public ResponseEntity<Invoice> createInvoice(@RequestBody Invoice invoice){
        System.out.println(" ----- InvoiceRessource/createInvoice ----- ");

        invoiceService.create(invoice);

        return ResponseEntity.status(HttpStatus.CREATED).body(invoice);
    }

    /*@PutMapping("/{id}")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable("id") String number, @RequestBody Invoice updatedInvoice) {
        System.out.println(" ----- updateInvoice ----- ");

        Invoice existingInvoice = invoiceService.getInvoiceByNumber(number);

        if (existingInvoice != null) {
            existingInvoice.setCustomerName(updatedInvoice.getCustomerName());
            existingInvoice.setOrderNumber(updatedInvoice.getOrderNumber());
            invoiceService.create(existingInvoice); // Utiliser un service update si disponible
            return ResponseEntity.ok(existingInvoice);

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }*/

    /*@DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable("id") String number) {
        System.out.println(" ----- deleteInvoice ----- ");

        Invoice existingInvoice = invoiceService.getInvoiceByNumber(number);

        if (existingInvoice != null) {
            invoiceService.delete(existingInvoice);
            return ResponseEntity.noContent().build();

        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }*/

    /**
     * GETTERS/SETTERS
     */
    public IInvoiceService getInvoiceService() {
        return invoiceService;
    }

    public void setInvoiceService(IInvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }
}