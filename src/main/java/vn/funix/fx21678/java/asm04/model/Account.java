package vn.funix.fx21678.java.asm04.model;

import vn.funix.fx21678.java.asm04.common.IReport;
import vn.funix.fx21678.java.asm04.dao.AccountDao;
import vn.funix.fx21678.java.asm04.dao.CustomerDao;
import vn.funix.fx21678.java.asm04.dao.TransactionDao;

import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Account implements Serializable, IReport {
    private static final String COMMA_DELIMITER = ",";
    private static final long serialVersionUID = 1L;


    private String accountNumber;
    private double balance;
    private String customerId;

    public Account() {
    }

    public Account(String customerId, String accountNumber, double balance) {
        this.customerId = customerId;
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public Account(String[] arr) {
        this.customerId = arr[0];
        this.accountNumber = arr[1];
        this.balance = Double.parseDouble(arr[2]);
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public boolean isPremium() {
        if (this.balance < 10000000)
            return false;
        return true;
    }

    public Customer getCustomer() {
        List<Customer> customers = CustomerDao.list();
        return customers.stream().filter(item -> item.getCustomerId().equals(this.customerId)).findFirst().get();
    }

    public List<Transaction> getTransactions() throws IOException {
        List<Transaction> ls = TransactionDao.list();
        List<Transaction> rs = ls.stream().filter(item -> item.getAccountNumber().equals(this.accountNumber)).collect(Collectors.toList());
        return rs;
    }

    public void displayTransactionsList() throws IOException {
        List<Transaction> transactions = getTransactions();
        for (Transaction transaction : transactions) {
            transaction.show();
        }
    }

    public void createTransaction(double amount, String time, boolean status, TransactionType type) throws IOException {
        List<Transaction> transactions = TransactionDao.list();
        Transaction transaction = new Transaction(String.valueOf(transactions.size()), this.accountNumber, amount, time, status, type);
        transactions.add(transaction);
        TransactionDao.save(transactions);
    }

    public void input(Scanner scanner) {
        // tao acc


        //tao transaction


    }

    @Override
    public void log(double amount, TransactionType type, String receiveAccount) {
        Locale locale = new Locale("vi", "VI");
        String pattern = "###,###,###,###";
        DecimalFormat dcf = (DecimalFormat) NumberFormat.getNumberInstance(locale);
        dcf.applyPattern(pattern);
        System.out.println("+------------------------------------------------------+");
        System.out.println("|                BIEN LAI GIAO DICH SAVINGS            |");
        System.out.printf("|%-24s%30s|\n", "Ngay GD: ", String.valueOf(LocalDateTime.now()));
        System.out.printf("|%-24s%30s|\n", "ATM ID: ", "DIGITAL-BANK-ATM " + LocalDate.now().getYear());
        System.out.printf("|%-24s%30s|\n", "So TK: ", this.accountNumber);
        if (type.equals(TransactionType.TRANSFERS)) {
            System.out.printf("|%-24s%30s|\n", "So TK NHAN: ", receiveAccount);
            System.out.printf("|%-24s%30s|\n", "So tien chuyen: ", dcf.format(amount) + "đ");
        } else
            System.out.printf("|%-24s%30s|\n", "So tien rut: ", dcf.format(amount) + "đ");
        System.out.printf("|%-24s%30s|\n", "So du: ", dcf.format(this.balance) + "đ");
        System.out.printf("|%-24s%30s|\n", "Phi + VAT:", "0đ");
        System.out.println("+------------------------------------------------------+");
    }

    public boolean isAccepted(double amount) {
        return this.getBalance() - amount >= 50000 && amount % 10000 == 0 && amount >= 50000;
    }
}
