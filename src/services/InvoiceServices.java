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
        PreparedStatement statement = con.prepareStatement("INSERT INTO invoices (customer_id,vehicles_id,service_id) VALUES (?,?,?)");

        for(int i = 0; i<selectedServices.size(); i++){
            statement.setInt(1,customer.getId());
            statement.setInt(2,vehicle.getId());
            statement.setInt(3,selectedServices.get(i).getId());
        }
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

    public List<Invoice> getAllInvoice() throws SQLException{
        Connection con = db.getConnection();
        List<Invoice> allInvoices= new ArrayList<>();
        Statement statement = con.createStatement();
        ResultSet Rs =  statement.executeQuery("SELECT * FROM invoices");


        return allInvoices;


    }


}
