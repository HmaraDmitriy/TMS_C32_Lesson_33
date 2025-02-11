package org.bankAccount;

public class Main {
    public static void main(String[] args) {

        Account.createAccount("123456", 5000);
        Account.createAccount("654321", 2000);

        System.out.println("Balance of 123456: " + Account.getBalance("123456"));
        System.out.println("Balance of 654321: " + Account.getBalance("654321"));

        Account.transferMoney("123456", "654321", 1000);

        System.out.println("Balance of 123456 after transfer: " + Account.getBalance("123456"));
        System.out.println("Balance of 654321 after transfer: " + Account.getBalance("654321"));
    }
}
