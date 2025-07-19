package entity;

public class Customer {
    private int id;
    private String name;
    private String phone;
    private Vehicle vehicle;
    private Invoice invoice;

    public Customer(String name, String phone, Vehicle vehicle) {

        this.name = name;
        this.phone = phone;
        this.vehicle = vehicle;
    }

    public Customer(int id, String name, String phone) {
        this.id = id;
        this.name = name;
        this.phone = phone;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return STR."customers{id=\{id}, name='\{name}', phone='\{phone}'}";
    }
}
