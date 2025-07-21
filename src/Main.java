import entity.Customer;
import entity.Invoice;
import entity.Service;
import entity.Vehicle;
import services.CustomerServices;
import services.InvoiceServices;
import services.Services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final CustomerServices CUSTOMER_SERVICES = new CustomerServices();
    private static final InvoiceServices INVOICE_SERVICES = new InvoiceServices();
    private static final Services SERVICES = new Services();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n===== Welcome to Our Service Portal =====");
            System.out.println("Please choose a service:");
            System.out.println("1. Add Customer");
            System.out.println("2. Generate Invoice");
            System.out.println("3. Vehicle Details");
            System.out.println("4. Customer Details");
            System.out.println("5. Exit");
            System.out.print("Enter your choice (1-5): ");

            int choice;
            try {
                choice = sc.nextInt();
                sc.nextLine(); // consume newline
            } catch (Exception e) {
                System.out.println("❌ Invalid input. Please enter a number between 1 and 5.");
                sc.nextLine(); // clear invalid input
                continue;
            }

            if (choice == 5) {
                System.out.println("✅ Exiting the Service Portal. Goodbye!");
                break;
            }

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

                    try {
                        CUSTOMER_SERVICES.createCustomers(
                                new Customer(name, phone, new Vehicle(vNo, model))
                        );
                        System.out.println("✅ Customer added successfully!");
                    } catch (SQLException e) {
                        System.out.println("❌ Error adding customer: " + e.getMessage());
                    }
                    break;

                case 2:
                    System.out.println("\n--- Generate Invoice ---");
                    try {
                        List<Customer> customers = CUSTOMER_SERVICES.getAllCustomers();
                        if (customers.isEmpty()) {
                            System.out.println("❌ No customers found.");
                            break;
                        }
                        System.out.println("Available Customers:");
                        for (Customer c : customers) {
                            System.out.println(c);
                        }
                        System.out.print("Select Customer (ID): ");
                        int id = sc.nextInt();
                        Customer customer = CUSTOMER_SERVICES.getACustomer(id);
                        if (customer != null && customer.getVehicle() != null) {
                            List<Service> selectedServices = new ArrayList<>();
                            List<Service> allServices = SERVICES.getAllServices();
                            System.out.println("Available Services: " + allServices);
                            System.out.printf("Select Service IDs [0 - %d], type -1 to finish:\n", allServices.size() - 1);
                            while (true) {
                                System.out.print("Enter service ID (-1 to stop): ");
                                int serviceId = sc.nextInt();
                                if (serviceId == -1) break;
                                if (serviceId >= 0 && serviceId < allServices.size()) {
                                    selectedServices.add(allServices.get(serviceId));
                                    System.out.println("Service added.");
                                } else {
                                    System.out.println("Invalid ID. Try again.");
                                }
                            }
                            INVOICE_SERVICES.addInvoice(customer, customer.getVehicle(), selectedServices);
                            System.out.println("✅ Invoice generated successfully!");
                        } else {
                            System.out.println("❌ No customer or vehicle found with ID: " + id);
                        }
                    } catch (SQLException e) {
                        System.out.println("❌ Error generating invoice: " + e.getMessage());
                    }
                    break;

                case 3:
                    System.out.println("\n--- Vehicle Details ---");
                    System.out.print("Enter Customer ID: ");
                    int custId = sc.nextInt();
                    try {
                        Customer customerWithVehicle = CUSTOMER_SERVICES.getACustomer(custId);
                        if (customerWithVehicle != null && customerWithVehicle.getVehicle() != null) {
                            System.out.println("Customer: " + customerWithVehicle.getName());
                            System.out.println("Vehicle Details: " + customerWithVehicle.getVehicle());
                        } else {
                            System.out.println("❌ No customer or vehicle found with ID: " + custId);
                        }
                    } catch (SQLException e) {
                        System.out.println("❌ Error retrieving vehicle details: " + e.getMessage());
                    }
                    break;

                case 4:
                    System.out.println("\n--- Customer Details ---");
                    try {
                        List<Customer> allCustomers = CUSTOMER_SERVICES.getAllCustomers();
                        if (allCustomers.isEmpty()) {
                            System.out.println("❌ No customers found.");
                        } else {
                            System.out.println("All Customers:");
                            for (Customer c : allCustomers) {
                                String vehicleInfo = c.getVehicle() != null ? c.getVehicle().toString() : "No vehicle registered";
                                System.out.println(c + ", Vehicle: " + vehicleInfo);
                            }
                        }
                    } catch (SQLException e) {
                        System.out.println("❌ Error retrieving customer details: " + e.getMessage());
                    }
                    break;

                default:
                    System.out.println("❌ Invalid choice. Please select a valid option (1-5).");
            }
        }

        sc.close();
    }
}