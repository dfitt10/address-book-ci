package com.example.addressbook.model;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteConnection {
    private static Connection instance = null;

    private SqliteConnection() {
        String url = "jdbc:sqlite:C:/Users/danzo/IdeaProjects/address-book/contacts.db";
        try {
            // Force the JVM to locate and register the implicit SQLite driver
            Class.forName("org.sqlite.JDBC");
            instance = DriverManager.getConnection(url);
        } catch (ClassNotFoundException e) {
            System.err.println("Could not find the SQLite JDBC Driver class: " + e.getMessage());
        } catch (SQLException sqlEx) {
            System.err.println("Database connection failed: " + sqlEx);
        }
    }

    public static Connection getInstance() {
        if (instance == null) {
            new SqliteConnection();
        }
        return instance;
    }
}


//public class SqliteConnection {
//    private static Connection instance = null;
//
//    private SqliteConnection() {
//        String url = "jdbc:sqlite:C:/Users/danzo/IdeaProjects/address-book/contacts.db";
//        try {
//            instance = DriverManager.getConnection(url);
//        }  catch (SQLException sqlEx) {
//            System.err.println(sqlEx);
//        }
//    }
//
//    public static Connection getInstance() {
//        if (instance == null) {
//            new SqliteConnection();
//        }
//        return instance;
//    }
//}
