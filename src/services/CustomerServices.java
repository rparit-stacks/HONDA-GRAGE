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

        PreparedStatement statement = con.prepareStatement("INSERT INTO customers (name,phone) VALUES (?,?)");
        PreparedStatement statement2 = con.prepareStatement("INSERT INTO vehicles (customer_id,number_plate,model) VALUES (?,?,?)");
        statement.setString(1, customer.getName());
        statement.setString(2, customer.getPhone());

        statement2.setInt(1, customer.getId());
        statement2.setString(2, customer.getVehicle().getNumber_plate());
        statement2.setString(3, customer.getVehicle().getModel());

        statement.executeUpdate();
        statement2.executeUpdate();
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
        ResultSet rs = statement.executeQuery("SELECT * from  customers");
        while(rs.next()){
            if(rs.getInt("id") == id) return  new Customer(rs.getInt("id"), rs.getString("name"), rs.getString("phone"));
        }
        return null;

    }

}
