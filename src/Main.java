import entity.Customer;
import entity.Invoice;
import entity.Service;
import entity.Vehicle;
import services.CustomerServices;
import services.InvoiceServices;
import services.Services;

import java.security.Principal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final CustomerServices CUSTOMER_SERVICES = new CustomerServices();
    private static final InvoiceServices INVOICE_SERVICES = new InvoiceServices();
    private static final Services SERVICES = new Services();
    public static void main(String[] args) throws SQLException {



        System.out.println("===== Welcome to Our Service Portal =====");
        System.out.println("Please choose a service:");
        System.out.println("1. Add Customer");
        System.out.println("2. Generate Invoice");
        System.out.println("3. Vehicle Details");
        System.out.println("4. Customer Details");
        System.out.print("Enter your choice (1-4): ");

        int choice = sc.nextInt();
        sc.nextLine(); // consume newline

        switch (choice) {
            case 1:
                System.out.println("\n--- Add Customer ---");

                System.out.print("Enter your Name: ");
                String name = sc.nextLine();

                System.out.print("Enter your Phone Number: ");
                String phone = sc.nextLine();

                System.out.print("Enter your Vehicle Number: ");
                String vNo = sc.nextLine();

                System.out.print("Enter Vehicle Model Year (e.g., 2015): ");
                String model = sc.nextLine();

                // Creating and storing customer details
                CUSTOMER_SERVICES.createCustomers(
                        new Customer(name, phone,
                                new Vehicle(vNo, model))
                );

                System.out.println("✅ Customer added successfully!\n");
                break;

            case 2:
                System.out.println("You selected: Generate Invoice");
                CUSTOMER_SERVICES.getAllCustomers();
                System.out.println("Select Customer(Id) : ");
                int id = sc.nextInt();
                Customer customer = CUSTOMER_SERVICES.getACustomer(id);
                if (customer != null) {

                    List<Service> selectedServices = new ArrayList<>();

                    System.out.println(SERVICES.getAllServices());
                    System.out.printf("Select Service IDs [0 - %d], type -1 to finish:\n", SERVICES.getAllServices().size() - 1);

                    while (true) {
                        System.out.print("Enter service ID (-1 to stop): ");
                        int serviceId = sc.nextInt();

                        if (serviceId == -1) break;

                        if (serviceId >= 0 && serviceId < SERVICES.getAllServices().size()) {
                            selectedServices.add(SERVICES.getAllServices().get(serviceId));
                            System.out.println("Service added.");
                        } else {
                            System.out.println("Invalid ID. Try again.");
                        }
                    }

                    INVOICE_SERVICES.addInvoice(customer,customer.getVehicle(),selectedServices);

                } else {
                    System.out.println("❌ No customer found with ID: " + id);
                }




                break;
            case 3:
                System.out.println("You selected: Vehicle Details");
                // Vehicle detail logic here
                break;
            case 4:
                System.out.println("You selected: Customer Details");
                // Customer detail logic here
                break;
            default:
                System.out.println("Invalid choice. Please select a valid option (1-4).");
        }

        sc.close();

    }
}