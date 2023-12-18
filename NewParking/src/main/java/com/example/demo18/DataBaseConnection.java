package com.example.demo18;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {
    public Connection con;
    public Connection getConnection(){
        String url = "jdbc:postgresql:parking-systemdb";
        String username = "postgres";
        String password = "123456";
        try {
            con = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected");
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return con;
    }
}
