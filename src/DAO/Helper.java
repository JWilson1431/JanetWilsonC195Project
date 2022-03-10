package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class Helper {
    //ObservableList<FirstLevelDivision> divisions = FXCollections.observableArrayList();
   private static Locale currentUserLocale;
   private static ZoneId currentUserZoneId;
   private static User currentUser;


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
          //  String country = rs.getString("12");


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
            int userId1 = rs.getInt(1);
            String userName1 = rs.getString(2);
            String password1 = rs.getString(3);

            currentUser = new User(userId1,userName1,password1);
            currentUserLocale = Locale.getDefault();
            currentUserZoneId = ZoneId.systemDefault();
            return true;
        }
        else{
            return false;
        }
        }

    //getter for currentUserLocale
    public static Locale getCurrentUserLocale() {
        return currentUserLocale;
    }

    //setter for currentUserLocale
    public static void setCurrentUserLocale(Locale currentUserLocale) {
        Helper.currentUserLocale = currentUserLocale;
    }

    //getter for CurrentUserZoneId
    public static ZoneId getCurrentUserZoneId() {
        return currentUserZoneId;
    }

    //setter for currentUserZoneId
    public static void setCurrentUserZoneId(ZoneId currentUserZoneId) {
        Helper.currentUserZoneId = currentUserZoneId;
    }

    //getter for CurrentUser
    public static User getCurrentUser() {
        return currentUser;
    }

    //setter for currentUser
    public static void setCurrentUser(User currentUser) {
        Helper.currentUser = currentUser;
    }

    //method to delete a customer from the customer table
        public static int deleteCustomer(int customerId) throws SQLException {
            String sql = "DELETE FROM Customers WHERE Customer_ID = ?";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ps.setInt(1, customerId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected;
        }

        //method to delete an Appointment
        public static int deleteAppt(int apptId) throws SQLException {
            String sql = "DELETE FROM Appointments WHERE Appointment_ID = ?";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ps.setInt(1,apptId);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected;
        }

        //method to update an appointment
    public static int updateAppt(String title, String description, String location, String type, ZonedDateTime start, ZonedDateTime end, int customerId, int userId, int contactId, int apptId) throws SQLException {
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startToString = start.format(formatter);
        String endToString = end.format(formatter);

        ps.setString(1,title);
        ps.setString(2,description);
        ps.setString(3,location);
        ps.setString(4,type);
        ps.setString(5,startToString);
        ps.setString(6,endToString);
        ps.setInt(7,customerId);
        ps.setInt(8,userId);
        ps.setInt(9,contactId);
        ps.setInt(10,apptId);

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

    public static ObservableList<String> getAllTypes(){
        ObservableList<String> types = FXCollections.observableArrayList();

        types.add("Associate meeting");
        types.add("Management meeting");
        types.add("Conference");
        types.add("Proposal meeting");
        return types;
    }

    public static ObservableList<Contact> getAllContacts() throws SQLException {
        ObservableList<Contact> contacts = FXCollections.observableArrayList();
        String sql = "SELECT * FROM contacts";

        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while(rs.next()){
            int contactId = rs.getInt(1);
            String contactName = rs.getString(2);
            String email = rs.getString(3);

            Contact contact1 = new Contact(contactId, contactName, email);
            contacts.add(contact1);
        }
            return contacts;

    }

    public static ObservableList<Country> getAllCountries() throws SQLException {
        ObservableList<Country> countries = FXCollections.observableArrayList();

        String sql = "SELECT Country, Country_ID FROM countries";


        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String countryName = rs.getString(1);
            int countryId = rs.getInt(2);
            Country country1 = new Country(countryId, countryName);
            countries.add(country1);
        }

        return countries;
    }



    public static ObservableList<FirstLevelDivision> getDivision(int countryID) throws SQLException {
         ObservableList<FirstLevelDivision> divisions = FXCollections.observableArrayList();

        String sql = "SELECT * FROM first_level_divisions WHERE Country_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, countryID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String division = rs.getString(2);
            int divisionId = rs.getInt(1);
            int countryId = rs.getInt(7);
            FirstLevelDivision division1 = new FirstLevelDivision(divisionId,division,countryID);
            divisions.add(division1);
        }
        return divisions;
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

    public static int addAppointment(String title, String description, String location, String type, ZonedDateTime start, ZonedDateTime end, int customerId, int userId, int contactId ) throws SQLException {
        String sql = "INSERT into appointments(Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES (?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startToString = start.format(formatter);
        String endToString = end.format(formatter);



        ps.setString(1,title);
        ps.setString(2,description);
        ps.setString(3,location);
        ps.setString(4,type);
        ps.setString(5, startToString);
        ps.setString(6, endToString);
        ps.setInt(7,customerId);
        ps.setInt(8,userId);
        ps.setInt(9,contactId);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;

    }

    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

        String sql = "SELECT * FROM appointments";

        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int appointmentId = rs.getInt(1);
            String title = rs.getString(2);
            String description = rs.getString(3);
            String location= rs.getString(4);
            String type = rs.getString(5);
            Timestamp start = rs.getTimestamp(6);
            Timestamp end = rs.getTimestamp(7);
            int customerId = rs.getInt(12);
            int userId = rs.getInt(13);
            int contactId = rs.getInt(14);


            allAppointments.add(new Appointment(appointmentId,title,description,location,type,start, end, customerId,userId,contactId));
        }
        return allAppointments;
    }


    public static ObservableList<User> getAllUsers() throws SQLException {
        ObservableList<User> users = FXCollections.observableArrayList();

        String sql = "SELECT * FROM users";

        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int userId = rs.getInt(1);
            String userName = rs.getString(2);
            String password = rs.getString(3);

            users.add(new User(userId, userName, password));
        }
        return users;
    }

    }