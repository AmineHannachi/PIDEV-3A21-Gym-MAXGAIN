package com.example.maxgain_store;

import java.sql.*;

public class MySQLDatabaseConnection {
    // JDBC URL, username, and password of MySQL server
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/maxgain";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    // Singleton instance
    private static MySQLDatabaseConnection instance;

    // Connection object
    private Connection connection;

    // Private constructor to prevent instantiation from outside
    private MySQLDatabaseConnection() {
        try {
            // Register JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish connection
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to get the singleton instance
    public static synchronized MySQLDatabaseConnection getInstance() {
        if (instance == null) {
            instance = new MySQLDatabaseConnection();
        }
        return instance;
    }
    public int getRowCount(String tableName) {
        int rowCount = 0;
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) FROM " + tableName);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                rowCount = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowCount;
    }
    public ResultSet executeQuery(String sqlQuery) {
        ResultSet resultSet = null;
        try {
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
    // Method to get the database connection
    public Connection getConnection() {
        return connection;
    }

    // Method to close the database connection
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
