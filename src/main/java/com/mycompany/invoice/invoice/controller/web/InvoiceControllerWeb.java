package com.mycompany.invoice.invoice.controller.web;

import com.mycompany.invoice.invoice.form.InvoiceForm;
import com.mycompany.invoice.invoice.service.invoice.IInvoiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class InvoiceControllerWeb {

    /**
     * ATTRIBUTS
     */
    private IInvoiceService invoiceService;

    @Value("${customer.service.url}")
    private String customerServiceUrl;

    /**
     * CONSTRUCTEUR
     */
    @Autowired
    public InvoiceControllerWeb(IInvoiceService invoiceService){
        this.invoiceService = invoiceService;
    }

    /**
     * METHODS
     */
    @GetMapping("/home")
    public String displayHome(){
        System.out.println(" ----- displayHome ----- ");
        return "index";
    }

    @GetMapping("/local-country")
    public String displayLocalCountry(){
        System.out.println(" ----- displayLocalCountry ----- ");
        return "local-country";
    }

    @GetMapping("/invoice/invoice-list")
    public String displayInvoiceList(){
        System.out.println(" ----- InvoiceControllerWeb/displayListInvoice ----- ");
        return "invoice-list";
    }

    @GetMapping("/invoice/add")
    public String addInvoice(Model model){
        System.out.println(" ----- InvoiceControllerWeb/addInvoice ----- ");
        model.addAttribute("invoiceForm", new InvoiceForm());
        model.addAttribute("customerServiceUrl", customerServiceUrl);
        return "invoice-add";
    }

    @PostMapping("/invoice/add-new-invoice")
    public String createInvoice(
            @Valid @ModelAttribute("invoiceForm") InvoiceForm invoiceForm,
            BindingResult bindingResult,
            Model model
        ){
        System.out.println(" ----- InvoiceControllerWeb/createInvoice ----- ");

        if (bindingResult.hasErrors()) {
            System.out.println(" ----- bindingResult : " + bindingResult);
            return "invoice-add";
        }

        // Ajout de la facture (et client si necessaire)
        invoiceService.createInvoice(invoiceForm);

        // Modification du message pour la page suivante
        model.addAttribute("message", "Invoice successfully submitted!");

        return "invoice-add-success";
    }

    /**
     * GETTERS/SETTERS
     */
    public String getCustomerServiceUrl() {
        return customerServiceUrl;
    }

    public void setCustomerServiceUrl(String customerServiceUrl) {
        this.customerServiceUrl = customerServiceUrl;
    }

}