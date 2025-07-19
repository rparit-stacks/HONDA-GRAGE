package entity;

import services.Services;

public class Invoice {
    private int id;
    private int customer_id;
    private int vehicles_id;
    private int service_id;
    private Customer customer;
    private Service service;

    public Invoice(int id, Customer customer, Service service) {
        this.id = id;
        this.customer = customer;
        this.service = service;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "customer=" + customer +
                ", service=" + service +
                ", id=" + id +
                '}';
    }
}
