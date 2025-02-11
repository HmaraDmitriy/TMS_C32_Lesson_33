package org.bankAccount;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Account {
    public static void createAccount(String accountNumber, double initialBalance) {
        String sql = "INSERT INTO accounts (account_number, balance) VALUES (?, ?)";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accountNumber);
            stmt.setBigDecimal(2, java.math.BigDecimal.valueOf(initialBalance));
            stmt.executeUpdate();
            System.out.println("Account created successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static double getBalance(String accountNumber) {
        String sql = "SELECT balance FROM accounts WHERE account_number = ?";
        double balance = -1;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, accountNumber);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                balance = rs.getBigDecimal("balance").doubleValue();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return balance;
    }

    public static void transferMoney(String fromAccount, String toAccount, double amount) {
        String withdraw= "UPDATE accounts SET balance = balance - ? WHERE account_number = ?";
        String deposit = "UPDATE accounts SET balance = balance + ? WHERE account_number = ?";

        try (Connection conn = ConnectionDB.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement withdrawStmt = conn.prepareStatement(withdraw);
                 PreparedStatement depositStmt = conn.prepareStatement(deposit)) {

                if (getBalance(fromAccount) < amount) {
                    throw new SQLException("Insufficient funds!");
                }

                withdrawStmt.setBigDecimal(1, java.math.BigDecimal.valueOf(amount));
                withdrawStmt.setString(2, fromAccount);
                withdrawStmt.executeUpdate();

                depositStmt.setBigDecimal(1, java.math.BigDecimal.valueOf(amount));
                depositStmt.setString(2, toAccount);
                depositStmt.executeUpdate();

                conn.commit();
                System.out.println("Transaction successful!");
            } catch (SQLException e) {
                conn.rollback();
                System.out.println("Transaction failed!");
                e.printStackTrace();
            } finally {
                conn.setAutoCommit(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
