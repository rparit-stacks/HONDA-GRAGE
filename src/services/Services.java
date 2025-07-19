package services;

import config.db;
import entity.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Services {

     public List<Service> getAllServices() throws SQLException {
         Connection con = db.getConnection();
         List<Service> allServices= new ArrayList<>();
         Statement statement = con.createStatement();
         ResultSet Rs =  statement.executeQuery("SELECT * FROM Services");

         while (Rs.next()){
             allServices.add(new Service(Rs.getInt("id"),Rs.getString("description"), Rs.getDouble("cost")));
         }
         return allServices;
     }

}
