package com.example.demo18;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class ExpiredRecordsTask {

    public static void checkAndDeleteExpiredRecords() {
        Connection con = null;
        PreparedStatement pst = null;

        try {
            
            String url = "jdbc:postgresql:registration";
            String username = "postgres";
            String password = "123456";
            con = DriverManager.getConnection(url, username, password);

            
            String deleteQuery = "DELETE FROM parking WHERE dateTo < ?";
            pst = con.prepareStatement(deleteQuery);
            pst.setDate(1, java.sql.Date.valueOf(LocalDate.now()));
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            
            try {
                if (pst != null) {
                    pst.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
