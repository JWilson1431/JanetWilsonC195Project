package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Helper {



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

/*
        public static ObservableList<String> getAllCountries() throws SQLException {
            public static ObservableList<String> countries = FXCollections.observableArrayList();

                String sql = "SELECT Country FROM countries";


                PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
                ResultSet rs = ps.executeQuery();

                while (rs.next()) {
                    countries.add(rs.getString(1));
                }

            return countries;
        }

        public static ObservableList<String> getDivision (String countryName) throws SQLException {

            ObservableList<String> divisions = FXCollections.observableArrayList();

            String sql = "SELECT Country_ID from countries WHERE Country = ?

            PreparedStatement ps = DBConnection.connection.prepareStatement(sql);
            ps.setString(1, );

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                divisions.add(rs.getString(2));
            }
            return divisions;
        }

 */
    }