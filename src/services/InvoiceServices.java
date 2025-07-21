package services;

import config.db;
import entity.Customer;
import entity.Invoice;
import entity.Service;
import entity.Vehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceServices {

    public void addInvoice(Customer customer, Vehicle vehicle, List<Service> selectedServices) throws SQLException {
        Connection con = db.getConnection();
        PreparedStatement statement = con.prepareStatement("INSERT INTO invoices (customer_id, vehicles_id, service_id) VALUES (?, ?, ?)");

        for (Service service : selectedServices) {
            statement.setInt(1, customer.getId());
            statement.setInt(2, vehicle.getId());
            statement.setInt(3, service.getId());
            statement.executeUpdate();
        }
        statement.close();
        con.close();
    }

//    public void addInvoice(Invoice invoice) throws SQLException {
//        Connection con = db.getConnection();
//        PreparedStatement statement = con.prepareStatement("INSERT INTO invoices (customer_id,vehicles_id,service_id) VALUES (?,?,?)");
//
//        statement.executeUpdate();
//        con.close();
//
//    }

    public List<Invoice> getAllInvoice() throws SQLException {
        Connection con = db.getConnection();
        List<Invoice> allInvoices = new ArrayList<>();
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(
                "SELECT i.id, c.id AS customer_id, c.name, c.phone, s.id AS service_id, s.description, s.cost " +
                        "FROM invoices i " +
                        "JOIN customers c ON i.customer_id = c.id " +
                        "JOIN services s ON i.service_id = s.id"
        );

        while (rs.next()) {
            Customer customer = new Customer(rs.getInt("customer_id"), rs.getString("name"), rs.getString("phone"));
            Service service = new Service(rs.getInt("service_id"), rs.getString("description"), rs.getDouble("cost"));
            Invoice invoice = new Invoice(rs.getInt("id"), customer, service);
            allInvoices.add(invoice);
        }

        rs.close();
        statement.close();
        con.close();
        return allInvoices;
    }




}
