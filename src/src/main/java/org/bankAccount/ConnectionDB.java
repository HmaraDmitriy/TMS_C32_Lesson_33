package org.bankAccount;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/* console - CREATE DATABASE bank_db;
             CREATE TABLE accounts (
        id BIGSERIAL PRIMARY KEY,
        account_number VARCHAR(20) UNIQUE NOT NULL,
        balance DECIMAL(15,2) NOT NULL CHECK (balance >= 0)
        );*/

public class ConnectionDB {
    private static final String URL = "jdbc:postgresql://localhost:5432/bank_db";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
