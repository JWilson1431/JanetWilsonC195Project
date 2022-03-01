package DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class helper {

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

    }