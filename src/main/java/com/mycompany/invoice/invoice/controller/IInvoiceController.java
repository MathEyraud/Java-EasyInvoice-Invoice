package com.mycompany.invoice.invoice.controller;

import com.mycompany.invoice.core.entity.invoice.Invoice;
import com.mycompany.invoice.invoice.service.invoice.IInvoiceService;
import org.springframework.ui.Model;

public interface IInvoiceController {
    String createInvoice(Invoice invoice, Model model);
    void setInvoiceService(IInvoiceService invoiceService);
}
