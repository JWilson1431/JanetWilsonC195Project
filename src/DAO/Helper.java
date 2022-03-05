package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.FirstLevelDivision;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Helper {

    public static ObservableList<Customer> getAllCustomers() throws SQLException {
    ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

        String sql = "SELECT * FROM customers";

        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int customerID = rs.getInt(1);
            String customerName = rs.getString(2);
            String address = rs.getString(3);
            String postalCode = rs.getString(4);
            String phone = rs.getString(5);
            int divisionId = rs.getInt(10);

            allCustomers.add(new Customer(customerID, customerName, address, postalCode, phone, divisionId));
        }
        return allCustomers;
    }

    public static boolean checkCredentials(String userName, String password) throws SQLException {
        String sql = "SELECT * from users WHERE User_Name =? AND Password=? ";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);

        ps.setString(1, userName);
        ps.setString(2,password);

        ResultSet rs = ps.executeQuery();
        if(rs.next()){
            return true;
        }
        else{
            return false;
        }
        }
        //method to delete a customer from the customer table
        public static int deleteCustomer(int customerId) throws SQLException {
            String sql = "DELETE FROM Customers WHERE Customer_ID = ?";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ps.setInt(1, customerId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected;
        }

        //method to update a customer
    public static int update(String name, String address, String postalCode, String phoneNum, int divId, int customerId) throws SQLException {

            String sql = "UPDATE customers SET Customer_Name = ?, Address = ?, Postal_Code = ?, Phone = ?, Create_Date = ?, Created_By = ?, Last_Update = ?, Last_Updated_By = ?, Division_ID = ? WHERE Customer_ID = ?";

            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);

            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, postalCode);
            ps.setString(4, phoneNum);
            ps.setString(5, null);
            ps.setString(6, null);
            ps.setString(7, null);
            ps.setString(8, null);
            ps.setInt(9, divId);
            ps.setInt(10, customerId);


            int rowsAffected = ps.executeUpdate();
            return rowsAffected;

    }


        //method to add a customer to the customer table
    public static int addCustomer(String name, String address, String postalCode, String phoneNum, int divId ) throws SQLException {
        String sql = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By,Division_ID) VALUES (?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);

        ps.setString(1,name);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phoneNum);
        ps.setString(5, null);
        ps.setString(6,null);
        ps.setString(7,null);
        ps.setString(8,null);
        ps.setInt(9,divId);


        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    }