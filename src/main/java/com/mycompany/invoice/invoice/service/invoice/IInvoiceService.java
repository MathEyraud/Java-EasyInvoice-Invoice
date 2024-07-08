package com.mycompany.invoice.invoice.service.invoice;

import com.mycompany.invoice.core.entity.invoice.Invoice;
import com.mycompany.invoice.invoice.form.InvoiceForm;
import com.mycompany.invoice.invoice.repository.IInvoiceRepository;

public interface IInvoiceService{

    Invoice create(Invoice invoice);
    void setInvoiceRepository(IInvoiceRepository invoiceRepository);
    Iterable<Invoice> getListInvoice();
    Invoice getInvoiceByNumber(String number);
    Invoice createInvoice(InvoiceForm invoiceForm);
}