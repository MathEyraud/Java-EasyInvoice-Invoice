package com.mycompany.invoice.invoice.service.invoice;

import com.mycompany.invoice.core.entity.customer.Address;
import com.mycompany.invoice.core.entity.customer.Customer;
import com.mycompany.invoice.core.entity.invoice.Invoice;
import com.mycompany.invoice.invoice.form.InvoiceForm;
import com.mycompany.invoice.invoice.repository.ICustomerRepository;
import com.mycompany.invoice.invoice.repository.IInvoiceRepository;
import com.mycompany.invoice.invoice.service.customer.ICustomerService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class InvoiceService implements IInvoiceService {

    /**
     * ATTRIBUTS
     */
    private IInvoiceRepository  invoiceRepository;
    private ICustomerRepository customerRepository;
    private ICustomerService    customerService;


    /**
     * CONSTRUCTEUR
     */
    @Autowired
    public InvoiceService(
            IInvoiceRepository invoiceRepository,
            ICustomerRepository customerRepository,
            ICustomerService customerService
    ){

        this.invoiceRepository  = invoiceRepository;
        this.customerRepository = customerRepository;
        this.customerService    = customerService;
    }

    /**
     * METHODS
     */
    @Transactional
    public Invoice create(Invoice invoice){

        System.out.println(" ----- InvoiceService/create ----- ");

        // On enregistre la facture
        return invoiceRepository.save(invoice);
    }

    @Transactional
    public Invoice createInvoice(InvoiceForm invoiceForm){

        System.out.println(" ----- InvoiceService/createInvoice ----- ");

        // Création du client pour l'ajout/la récupération
        Customer customer = new Customer();

        if (invoiceForm.isNewCustomer()) {

            // Création de l'adresse du client
            // Via les informations du formulaire
            Address address = new Address(
                    invoiceForm.getStreet(),
                    invoiceForm.getStreetNumber(),
                    invoiceForm.getCity(),
                    invoiceForm.getZipCode(),
                    invoiceForm.getCountry()
            );

            // AJOUTER LA NOUVELLE ADRESSE EN DB
            address = customerService.createAddress(address).block();

            // Ajout des données au client
            customer.setName(invoiceForm.getCustomerName());
            customer.setIdAddress(address.getId());

            // AJOUTER LE NOUVEAU CUSTOMER EN DB
            // Utiliser WebClient pour créer les customers
            customer = customerService.createCustomer(customer).block();

        } else {
            // RECUPERER LE CUSTOMER EN DB
            customer = customerService.getCustomerById(invoiceForm.getCustomerId()).block();
        }

        // LIER L'INVOICE AUX AUTRES INFORMATIONS
        Invoice invoice = new Invoice();
        invoice.setIdCustomer(customer.getId());
        invoice.setOrderNumber(invoiceForm.getOrderNumber());

        // On enregistre la facture
        return invoiceRepository.save(invoice);
    }

    @Override
    public Iterable<Invoice> getListInvoice(){
        System.out.println(" ----- InvoiceService/getListInvoice ----- ");
        return invoiceRepository.findAll();
    }

    @Override
    public Invoice getInvoiceByNumber(String number){
        System.out.println(" ----- InvoiceService/getInvoiceByNumber ----- ");
        return invoiceRepository.findById(number).orElseThrow(
                () -> new NoSuchElementException("Invoice with number " + number + " not found")
        );
    }

    /**
     * GETTERS/SETTERS
     */
    public IInvoiceRepository getInvoiceRepository() {
        return invoiceRepository;
    }

    public void setInvoiceRepository(IInvoiceRepository invoiceRepository) {
        this.invoiceRepository = invoiceRepository;
    }

    public ICustomerRepository getCustomerRepository() {
        return customerRepository;
    }

    public void setCustomerRepository(ICustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }
}