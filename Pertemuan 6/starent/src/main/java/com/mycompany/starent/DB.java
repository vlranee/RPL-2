package com.mycompany.starent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    private static final String URL = "jdbc:mysql://localhost:3306/db_starent";

    public static Connection get() throws SQLException {
        return DriverManager.getConnection(URL, "root", "");
    }
}