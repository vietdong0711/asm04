package vn.funix.fx21678.java.asm04.model;

import vn.funix.fx21678.java.asm04.common.IReport;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

public class Transaction implements Serializable{
    private static final long serialVersionUID = 1L;

    private String id;
    private String accountNumber;
    private double amount;
    private String time;
    private boolean status;
    private TransactionType type;

    public Transaction(){
    }

    public Transaction(String id, String accountNumber, double amount, String time, boolean status, TransactionType type) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.amount = amount;
        this.time = time;
        this.status = status;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id='" + id + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", amount=" + amount +
                ", time='" + time + '\'' +
                ", status=" + status +
                ", type=" + type +
                '}';
    }

    public void show(){
        Locale locale = new Locale("vi", "VI");
        String pattern = "###,###,###,###";
        DecimalFormat dcf = (DecimalFormat) NumberFormat.getNumberInstance(locale);
        dcf.applyPattern(pattern);
        System.out.printf("%-3s%9s |%18s|%17s | %18s\n", "[GD]", this.accountNumber, this.type, dcf.format(this.amount) + "đ", this.time );
    }
}
