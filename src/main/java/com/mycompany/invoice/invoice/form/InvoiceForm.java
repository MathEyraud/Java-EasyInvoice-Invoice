package com.mycompany.invoice.invoice.form;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class InvoiceForm {

    /**
     * ATTRIBUTS
     */
    private String number;

    @NotEmpty(message = "Customer name is required !")
    @Size(min = 3, max = 50, message = "Customer name must be between 3 and 50 characters !")
    private String customerName;
    private Long customerId;

    // Adresse du client
    // TODO : Il devrait y avoir des critères de validation normalement
    private boolean newCustomer;
    private String street;
    private String streetNumber;
    private String city;
    private String zipCode;
    private String country;

    //@Pattern(regexp = "^\\d{3}-\\d{3}-\\d{4}$", message = "Order number must follow the pattern XXX-XXX-XXXX")
    private String orderNumber;

    /**
     * GETTERS/SETTERS
     */
    public String getNumber() {
        return this.number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCustomerName() {
        return this.customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getOrderNumber() {
        return this.orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public boolean isNewCustomer() {
        return newCustomer;
    }

    public void setNewCustomer(boolean newCustomer) {
        this.newCustomer = newCustomer;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}