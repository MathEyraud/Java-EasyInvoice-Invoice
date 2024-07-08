package com.mycompany.invoice.invoice.controller.douchette;

import com.mycompany.invoice.invoice.controller.IInvoiceController;
import com.mycompany.invoice.core.entity.customer.Customer;
import com.mycompany.invoice.core.entity.invoice.Invoice;
import com.mycompany.invoice.invoice.service.invoice.IInvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;

//@Controller
public class InvoiceControllerDouchette implements IInvoiceController {

    /**
     * ATTRIBUTS
     */
    private IInvoiceService invoiceService;

    /**
     * CONSTRUCTEUR
     */
    @Autowired
    public InvoiceControllerDouchette(IInvoiceService invoiceService){
        this.invoiceService = invoiceService;
    }

    /**
     * METHODS
     */
    @Override
    public String createInvoice(Invoice invoice, Model model){

        // Todo : Cf message suivant (gestion de création d'utilisateur)
        // Pour l'ajout d'un client normalement il faut vérifier si le client n'existe pas déja
        // On va partir du principe que l'on crée un nouveau client à chaque fois pour gagner du temps
        // Mais cela n'est pas propre, car on aura plusieurs fois le même client
        Customer newCustomer = new Customer(invoice.getCustomer().getName());
        invoice.setCustomer(newCustomer);

        invoice.setOrderNumber(invoice.getOrderNumber());

        invoiceService.create(invoice);

        model.addAttribute("message", "Invoice successfully submitted!");

        return "invoice-add-success";
    }

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