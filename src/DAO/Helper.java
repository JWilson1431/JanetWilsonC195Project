package DAO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;

import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**This is the helper class. It contains methods to assist with tasks throughout the application.*/
public class Helper {
    //ObservableList<FirstLevelDivision> divisions = FXCollections.observableArrayList();
    private static Locale currentUserLocale;
    private static ZoneId currentUserZoneId;
    private static User currentUser;

    /**This is the get all customers method. This method gets an observable list of all customers from the database.
     * @return ObservableList of customers.
     * @throws SQLException*/
    //method to get an observable list of all customers from the database
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

    /**This is the check credentials method. This method checks the username and password to see if they are valid for logging into the system.
     * @param password - the password
     * @param userName -the username
     * @returns boolean
     * @throws SQLException*/
    //checks the username and password in the database for login
    public static boolean checkCredentials(String userName, String password) throws SQLException {
        String sql = "SELECT * from users WHERE User_Name =? AND Password=? ";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);

        ps.setString(1, userName);
        ps.setString(2, password);

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            int userId1 = rs.getInt(1);
            String userName1 = rs.getString(2);
            String password1 = rs.getString(3);

            currentUser = new User(userId1, userName1, password1);
            currentUserLocale = Locale.getDefault();
            currentUserZoneId = ZoneId.systemDefault();
            return true;
        } else {
            return false;
        }
    }

    /**This is the get current userlocale method. This method gets the locale of the current user.
     *@return Locale*/
    //getter for currentUserLocale
    public static Locale getCurrentUserLocale() {
        return currentUserLocale;
    }
    /**This is the set current user locale method. THis method sets the locale of the current user.
     * @param currentUserLocale - the current user locale*/
    //setter for currentUserLocale
    public static void setCurrentUserLocale(Locale currentUserLocale) {
        Helper.currentUserLocale = currentUserLocale;
    }
    /**This is the get current user zone id method. This method gets the zone id of the current user.
     * @return ZoneId - current users zone id*/
    //getter for CurrentUserZoneId
    public static ZoneId getCurrentUserZoneId() {
        return currentUserZoneId;
    }
    /**This is the set current user zone id method. This method sets the zone id for the current user.
     * @param currentUserZoneId -the zone id for the current user.*/
    //setter for currentUserZoneId
    public static void setCurrentUserZoneId(ZoneId currentUserZoneId) {
        Helper.currentUserZoneId = currentUserZoneId;
    }
    /**This is the get current user method. This method gets the current user.
     * @return User current user.*/
    //getter for CurrentUser
    public static User getCurrentUser() {
        return currentUser;
    }
    /**This is the set current user method. This method sets the current user.
     * @param currentUser - the current user.*/
    //setter for currentUser
    public static void setCurrentUser(User currentUser) {
        Helper.currentUser = currentUser;
    }
    /**This is the delete customer method. This method deletes a customer from the database.
     * @param customerId - the id of the customer.
     * @return int rowsAffected
     * @throws SQLException*/
    //method to delete a customer from the customer table
    public static int deleteCustomer(int customerId) throws SQLException {
        String sql = "DELETE FROM Customers WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
    /**This is the delete appointment method. This method deletes an appointment from the database.
     * @param apptId - the id of the appointment
     * @return int rowsAffected
     * @throws SQLException*/
    //method to delete an Appointment
    public static int deleteAppt(int apptId) throws SQLException {
        String sql = "DELETE FROM Appointments WHERE Appointment_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, apptId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
    /**This is the update appointment method. This method updates an appointment in the database.
     * @param apptId - the id of the appointment.
     * @param customerId -the id of the customer.
     * @param userId - the id of the user.
     * @param contactId -the id of the contact.
     * @param end -the end date and time.
     * @param start -the start date and time.
     * @param location -the location of the appointment.
     * @param description -the description of the appointment.
     * @param title the title of the appointment.
     * @param type the type of the appointment.
     * @return int rowsAffected
     * @throws SQLException*/
    //method to update an appointment
    public static int updateAppt(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId, int apptId) throws SQLException {
        String sql = "UPDATE appointments SET Title = ?, Description = ?, Location = ?, Type = ?, Start = ?, End = ?, Customer_ID = ?, User_ID = ?, Contact_ID = ? WHERE Appointment_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startToString = start.format(formatter);
        String endToString = end.format(formatter);

        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(startToString));
        ps.setTimestamp(6, Timestamp.valueOf(endToString));
        ps.setInt(7, customerId);
        ps.setInt(8, userId);
        ps.setInt(9, contactId);
        ps.setInt(10, apptId);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;

    }

    /**This is the update customer method. This method updates a customer.
     * @param customerId the id of the customer
     * @param postalCode the postal code of the customer
     * @param address the address of the customer
     * @param divId the division id of the customer
     * @param name the name of the customer
     * @param phoneNum the phone number of the customer
     * @return int rowsAffected
     * @throws SQLException*/
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
    /**This is the get all types method. This method gets an observable list of all of the types of appointments to choose from
     * @return observable list */
    //method to get all of the different types that are available for appointments
    public static ObservableList<String> getAllTypes() {
        ObservableList<String> types = FXCollections.observableArrayList();

        types.add("Associate meeting");
        types.add("Management meeting");
        types.add("Conference");
        types.add("Proposal meeting");
        return types;
    }
    /**This is the get all contacts method. This method gets an observable list of all contacts.
     * @return observable list
     * @throws SQLException*/
    //method to get a list with all contacts
    public static ObservableList<Contact> getAllContacts() throws SQLException {
        ObservableList<Contact> contacts = FXCollections.observableArrayList();
        String sql = "SELECT * FROM contacts";

        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int contactId = rs.getInt(1);
            String contactName = rs.getString(2);
            String email = rs.getString(3);

            Contact contact1 = new Contact(contactId, contactName, email);
            contacts.add(contact1);
        }
        return contacts;

    }
    /**This is the get user id method. This method uses the username parameter to get an id for the user.
     * @param username - the username of the user
     * @return int userID
     * @throws SQLException*/
    public static int getUserId(String username) throws SQLException {
        int userID = 1;
        String sql = "SELECT * FROM users WHERE User_Name = ? ";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int userId = rs.getInt(1);
            userID = userId;
        }
        return userID;
    }
    public static User getUserById(int userId) throws SQLException {
        User user1 = null;
        String sql = "SELECT * FROM users WHERE User_ID = ? ";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String userName = rs.getString("User_Name");
            String password = rs.getString("Password");
            user1 = new User(userId,userName,password);
        }
        return user1;
    }


    /**This is the get all countries method. This method gets an observable list of all countries.
     * @return observable list
     * @throws SQLException*/
    //method to get a list with all countries
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
    /**This is the get division method. This method uses the parameter of the countryID and gets the division.
     * @param countryID - the id of the country
     * @return observable list
     * @throws SQLException*/
    //method to get a division with the input of the countryID
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
            FirstLevelDivision division1 = new FirstLevelDivision(divisionId, division, countryId);
            divisions.add(division1);
        }
        return divisions;
    }

    public static FirstLevelDivision getDivisionById(int divisionId) throws SQLException {
        FirstLevelDivision division1 = null;

        String sql = "SELECT * FROM first_level_divisions WHERE Division_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, divisionId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String division = rs.getString("Division");
            int countryId = rs.getInt("Country_ID");
             division1 = new FirstLevelDivision(divisionId, division, countryId);

        }
        return division1;
    }

    public static Country getCountryById(int countryId) throws SQLException {
        Country country1 = null;

        String sql = "SELECT * FROM countries WHERE Country_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, countryId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String countryName = rs.getString("Country");

            country1 = new Country(countryId,countryName);

        }
        return country1;
    }

    /**This is the add customer method. This method adds a new customer into the database.
     *@param phoneNum - the phone number of the customer.
     * @param name the name of the customer.
     * @param divId the division id of the customer.
     * @param address the address of the customer.
     * @param postalCode the postalcode of the customer
     * @return int rowsAffected.
     * @throws SQLException*/
    //method to add a customer
    public static int addCustomer(String name, String address, String postalCode, String phoneNum, int divId) throws SQLException {
        String sql = "INSERT INTO customers(Customer_Name, Address, Postal_Code, Phone, Create_Date, Created_By, Last_Update, Last_Updated_By,Division_ID) VALUES (?,?,?,?,?,?,?,?,?)";
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


        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
    /**This is the add appointment method. This method adds a new appointment into the database.
     * @param customerId the id of the customer.
     * @param type the type of the appointment.
     * @param title the title of the appointment.
     * @param description the description of the appointment.
     * @param location the location of the appointment.
     * @param start the start of the appointment.
     * @param end the end of the appointment.
     * @param contactId the id of the contact.
     * @param userId the id of the user.
     * @return int rowsAffected
     * @throws SQLException
     * */
    //method to add an appointment
    public static int addAppointment(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId) throws SQLException {
        String sql = "INSERT into appointments(Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES (?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String startToString = start.format(formatter);
        String endToString = end.format(formatter);


        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(startToString));
        ps.setTimestamp(6, Timestamp.valueOf(endToString));
        ps.setInt(7, customerId);
        ps.setInt(8, userId);
        ps.setInt(9, contactId);

        int rowsAffected = ps.executeUpdate();
        return rowsAffected;

    }
    /**This is the get all appointments method. This method gets an observable lsit of all appointments.
     * @return observable list
     * @throws SQLException*/
    //method to get a list of all appointments
    public static ObservableList<Appointment> getAllAppointments() throws SQLException {
        ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

        String sql = "SELECT * FROM appointments";

        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int appointmentId = rs.getInt(1);
            String title = rs.getString(2);
            String description = rs.getString(3);
            String location = rs.getString(4);
            String type = rs.getString(5);
            Timestamp start = rs.getTimestamp(6);
            Timestamp end = rs.getTimestamp(7);
            int customerId = rs.getInt(12);
            int userId = rs.getInt(13);
            int contactId = rs.getInt(14);


            allAppointments.add(new Appointment(appointmentId, title, description, location, type, start, end, customerId, userId, contactId));
        }
        return allAppointments;
    }

    /**This is the get all users method. This method gets an observable list of all users.
     * @return observable list
     * @throws SQLException*/
    //method to get a list of all users
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
    /**This is the get appointments in 15 method. This appointment gets an observable list of appointments coming up within 15 minutes.
     * @param userId the id of the user.
     * @return observable list
     * @throws SQLException*/
    public static ObservableList<Appointment> getApptsIn15(int userId) throws SQLException {
        ObservableList<Appointment> appts = FXCollections.observableArrayList();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime now = LocalDateTime.now();
       // LocalDateTime nowWith15 = now.plusMinutes(15);
        ZonedDateTime userTimeNow = now.atZone(Helper.getCurrentUserZoneId());
        ZonedDateTime userTimePlus15 = userTimeNow.plusMinutes(15);
        // ZonedDateTime nowInUTC = userTimeNow.withZoneSameInstant((ZoneOffset.UTC));
       // ZonedDateTime nowWith15UTC = nowInUTC.plusMinutes(15);

        String sql = "SELECT * from appointments WHERE Start BETWEEN ? AND ? AND User_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);

        //
        String start = userTimeNow.format(formatter);
        String end = userTimePlus15.format(formatter);
        ps.setTimestamp(1, Timestamp.valueOf(start));
        ps.setTimestamp(2,Timestamp.valueOf(end));
        ps.setInt(3, userId);

        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int contactId1 = rs.getInt(14);
            int apptId = rs.getInt(1);
            String title = rs.getString(2);
            String description = rs.getString(3);
            String location = rs.getString(4);
            String type = rs.getString(5);
            Timestamp start1 = rs.getTimestamp(6);
            Timestamp end1 = rs.getTimestamp(7);
            int customerId = rs.getInt(12);
            int userId1 = rs.getInt(13);
            System.out.println(start+end);
            Appointment appointment = new Appointment(apptId, title, description, location, type, start1, end1, customerId, userId1, contactId1);
            appts.add(appointment);
        }
        return appts;
    }

    /**This is the filter by contact id method. This method uses a contact id parameter to created a filtered appointment list by contact.
     * @param contactId the id of the contact
     * @return observable list
     * @throws SQLException*/
    //method to return all appointments with a certain contact
    public static ObservableList<Appointment> filterByContactId(int contactId) throws SQLException {
        ObservableList<Appointment> filteredAppts = FXCollections.observableArrayList();
        ObservableList<Appointment> allAppts = FXCollections.observableArrayList();

        String sql = "SELECT * FROM appointments WHERE Contact_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, contactId);
        ResultSet rs = ps.executeQuery();


        while (rs.next()) {
            int contactId1 = rs.getInt(14);
            int apptId = rs.getInt(1);
            String title = rs.getString(2);
            String description = rs.getString(3);
            String location = rs.getString(4);
            String type = rs.getString(5);
            Timestamp start = rs.getTimestamp(6);
            Timestamp end = rs.getTimestamp(7);
            int customerId = rs.getInt(12);
            int userId = rs.getInt(13);

            allAppts.add(new Appointment(apptId, title, description, location, type, start, end, customerId, userId, contactId1));

        }
        return allAppts;
    }

    /**This is the filter by user Id method. This method creates an observable list of appointments filtered by a certain user id.
     * @param userId the id of the user.
     * @return observable list.
     * @throws SQLException*/
    //method to get all appointments with a certain userId
    public static ObservableList<Appointment> filterByUserId(int userId) throws SQLException {
        ObservableList<Appointment> filteredAppts = FXCollections.observableArrayList();
        ObservableList<Appointment> allAppts = FXCollections.observableArrayList();

        String sql = "SELECT * FROM appointments WHERE User_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, userId);
        ResultSet rs = ps.executeQuery();


        while (rs.next()) {
            int contactId1 = rs.getInt(14);
            int apptId = rs.getInt(1);
            String title = rs.getString(2);
            String description = rs.getString(3);
            String location = rs.getString(4);
            String type = rs.getString(5);
            Timestamp start = rs.getTimestamp(6);
            Timestamp end = rs.getTimestamp(7);
            int customerId = rs.getInt(12);
            int userId1 = rs.getInt(13);

            allAppts.add(new Appointment(apptId, title, description, location, type, start, end, customerId, userId1, contactId1));
        }
        return allAppts;
    }
    /**This is the filter by customer id method. This method uses a customer id parameter to create an observable list of filtered appointments.
     * @param customerId the id of the customer.
     * @return observable list
     * @throws SQLException*/
    //method to get all appointments with a certain customer id
    public static ObservableList<Appointment> filterByCustomerId(int customerId) throws SQLException {
        ObservableList<Appointment> filteredAppts = FXCollections.observableArrayList();
        // ObservableList<Appointment> allAppts = FXCollections.observableArrayList();

        String sql = "SELECT * FROM appointments WHERE Customer_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, customerId);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int contactId1 = rs.getInt(14);
            int apptId = rs.getInt(1);
            String title = rs.getString(2);
            String description = rs.getString(3);
            String location = rs.getString(4);
            String type = rs.getString(5);
            Timestamp start = rs.getTimestamp(6);
            Timestamp end = rs.getTimestamp(7);
            int customerId1 = rs.getInt(12);
            int userId = rs.getInt(13);

            filteredAppts.add(new Appointment(apptId, title, description, location, type, start, end, customerId1, userId, contactId1));
        }
        return filteredAppts;
    }

    /**This is the get all appointments with type method. This method gets an observable list of all appointments with a certain type.
     * @param type the type of appointment
     * @return observable list
     * @throws SQLException*/
    //method to get all appointments with a certain type
    public static ObservableList<Appointment> getAllApptsWithType(String type) throws SQLException {
        ObservableList<Appointment> allApptsWithType = FXCollections.observableArrayList();

        String sql = "SELECT * FROM appointments WHERE Type = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setString(1, type);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int contactId1 = rs.getInt(14);
            int apptId = rs.getInt(1);
            String title = rs.getString(2);
            String description = rs.getString(3);
            String location = rs.getString(4);
            String type1 = rs.getString(5);
            Timestamp start = rs.getTimestamp(6);
            Timestamp end = rs.getTimestamp(7);
            int customerId1 = rs.getInt(12);
            int userId = rs.getInt(13);

            allApptsWithType.add(new Appointment(apptId, title, description, location, type1, start, end, customerId1, userId, contactId1));
        }
        return allApptsWithType;

    }

    /**This is the get appointments filtered by month or week method. This method gets an observable list of appointments filtered by either week or month.
     * @param end the end
     * @param start the start
     * @return observable list
     * @throws SQLException*/
    //method to filter appointments by a certain time period
    public static ObservableList<Appointment> getApptsFilteredMonthWeek(LocalDate start, LocalDate end) throws SQLException {

        ObservableList<Appointment> appts = FXCollections.observableArrayList();


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String startString = start.format(formatter);
        String endString = end.format(formatter);

        String sql = "SELECT * FROM appointments WHERE Start between ? and ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setDate(1, Date.valueOf(startString));
        ps.setDate(2, Date.valueOf(endString));

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int contactId1 = rs.getInt(14);
            int apptId = rs.getInt(1);
            String title = rs.getString(2);
            String description = rs.getString(3);
            String location = rs.getString(4);
            String type1 = rs.getString(5);
            Timestamp start1 = rs.getTimestamp(6);
            Timestamp end1 = rs.getTimestamp(7);
            int customerId1 = rs.getInt(12);
            int userId = rs.getInt(13);

            Appointment appointment1 = new Appointment(apptId, title, description, location, type1, start1, end1, customerId1, userId, contactId1);

            appts.add(appointment1);
        }
        return appts;
    }
    /**This is the create month and type report method. This method creates a report of types of appointments per month.
     * @return String
     *@throws SQLException */
    //method to create a total of appointments per month
    public static String createMonthTypeReport() throws SQLException {
        //Use stringbuilder to create the month/type report
        StringBuilder monthType = new StringBuilder();


        String sql = "SELECT MONTHNAME(start) AS MONTH, TYPE, COUNT(Appointment_ID) as TOTAL FROM appointments GROUP BY MONTH, TYPE";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            monthType.append("Month: " + rs.getString("MONTH") + "\n" + " Number of " + rs.getString("TYPE") + " appointments: " + rs.getString("TOTAL") + "\n" + "\n");

        }
        return monthType.toString();
    }
    /**This is the validate business hours method. This method validates that the appointment being made is within business hours.
     * @param apptDate the date of the appointment
     * @param endDateTime the end date time of the appointment
     * @param startDateTime  the start date time of the appointment
     * @return boolean
     * @throws SQLException*/
    public static boolean validateBusinessHours(LocalDateTime startDateTime, LocalDateTime endDateTime, LocalDate apptDate) {
        //localdate time of business hours start and end
        LocalDateTime ldtBusinessHrsStart = LocalDateTime.of(apptDate, LocalTime.of(8, 0));
        LocalDateTime ldtBusinessHrsEnd = LocalDateTime.of(apptDate, LocalTime.of(22, 0));

        //zoneddatetime of business hours start and end
        ZonedDateTime zdtBusinessHrsStart = ZonedDateTime.of(ldtBusinessHrsStart, ZoneId.of("US/Eastern"));
        ZonedDateTime zdtBusinessHrsEnd = ZonedDateTime.of(ldtBusinessHrsEnd, ZoneId.of("US/Eastern"));

        //zoneddatetime of appointment start and end
        ZonedDateTime zdtStart = ZonedDateTime.of(startDateTime, getCurrentUserZoneId());
        ZonedDateTime zdtEnd = ZonedDateTime.of(endDateTime, getCurrentUserZoneId());

        if (zdtStart.isAfter(zdtEnd) || zdtStart.isBefore(zdtBusinessHrsStart) || zdtStart.isAfter(zdtBusinessHrsEnd) || zdtEnd.isBefore(zdtBusinessHrsStart) || zdtEnd.isAfter(zdtBusinessHrsEnd)) {
            return false;


        } else {
            return true;
        }
    }

    public static Customer getCustomerByID(int customerId) throws SQLException {

            Customer customer1 = null;

            String sql = "SELECT * FROM customers WHERE Customer_ID = ?";
            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ps.setInt(1, customerId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalcode = rs.getString("Postal_Code");
                String phone = rs.getString("Phone");
                int divisionId = rs.getInt("Division_ID");


                customer1 = new Customer(customerId,customerName,address,postalcode,phone,divisionId);


            }
            return customer1;
        }

    public static Contact getContactById(int contactId) throws SQLException {
        Contact contact1 = null;

        String sql = "SELECT * FROM contacts WHERE Contact_ID = ?";
        PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
        ps.setInt(1, contactId);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            String conTactName = rs.getString("Contact_Name");
            String email = rs.getString("Email");

            contact1 = new Contact(contactId, conTactName,email);

        }
        return contact1;
    }
    }



