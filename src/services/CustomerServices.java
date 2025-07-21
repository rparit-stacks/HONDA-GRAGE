package services;

import config.db;
import entity.Customer;
import entity.Vehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class CustomerServices {
    public static final Scanner sc=  new Scanner(System.in);
    public static final Connection con;

    static {
        try {
            con = db.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }




    public void createCustomers(Customer customer) throws SQLException {
        PreparedStatement statement = con.prepareStatement("INSERT INTO customers (name, phone) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, customer.getName());
        statement.setString(2, customer.getPhone());
        statement.executeUpdate();

        // Retrieve the generated customer ID
        ResultSet generatedKeys = statement.getGeneratedKeys();
        if (generatedKeys.next()) {
            customer.setId(generatedKeys.getInt(1));
        }

        // Insert into vehicles table
        PreparedStatement statement2 = con.prepareStatement("INSERT INTO vehicles (customer_id, number_plate, model) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
        statement2.setInt(1, customer.getId());
        statement2.setString(2, customer.getVehicle().getNumber_plate());
        statement2.setString(3, customer.getVehicle().getModel());
        statement2.executeUpdate();

        // Retrieve the generated vehicle ID
        generatedKeys = statement2.getGeneratedKeys();
        if (generatedKeys.next()) {
            customer.getVehicle().setId(generatedKeys.getInt(1));
        }

        statement.close();
        statement2.close();
    }

    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customersList = new ArrayList<>();
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * from  customers");

        while(rs.next()){
            customersList.add(new Customer(rs.getInt("id"), rs.getString("name"), rs.getString("phone")));
        }

        return customersList;
    }

    public Customer getACustomer(int id) throws SQLException {
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(
                "SELECT c.id, c.name, c.phone, v.id AS vehicle_id, v.number_plate, v.model " +
                        "FROM customers c LEFT JOIN vehicles v ON c.id = v.customer_id " +
                        "WHERE c.id = " + id
        );
        if (rs.next()) {
            Customer customer = new Customer(rs.getInt("id"), rs.getString("name"), rs.getString("phone"));
            if (rs.getInt("vehicle_id") != 0) {
                Vehicle vehicle = new Vehicle(rs.getString("number_plate"), rs.getString("model"));
                vehicle.setId(rs.getInt("vehicle_id"));
                customer.setVehicle(vehicle);
            }
            rs.close();
            statement.close();
            return customer;
        }
        rs.close();
        statement.close();
        return null;
    }
}
