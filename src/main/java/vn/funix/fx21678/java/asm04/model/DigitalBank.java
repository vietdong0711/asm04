package vn.funix.fx21678.java.asm04.model;

import vn.funix.fx21678.java.asm04.dao.AccountDao;
import vn.funix.fx21678.java.asm04.dao.CustomerDao;
import vn.funix.fx21678.java.asm04.dao.TransactionDao;
import vn.funix.fx21678.java.asm04.exception.CustomerIdNotValidException;
import vn.funix.fx21678.java.asm04.service.TextFileService;
import vn.funix.fx21678.java.asm04.utils.CheckCustomerIdUtils;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class DigitalBank extends Bank {

    private Scanner scanner = new Scanner(System.in);
    private List<String> lsCustomerId = new ArrayList<>();
    private List<String> lsAccountId = new ArrayList<>();
    private List<Account> lsAccount = new ArrayList<>();
    private List<Transaction> transactions = new ArrayList<>();
    private Map<Customer, List<Account>> map = new HashMap<>();
    private List<Customer> customers = new ArrayList<>();

    public DigitalBank() throws IOException {
        loadData();
    }

    public void loadData() throws IOException {
        customers = CustomerDao.list();
        lsCustomerId = customers.stream().map(User::getCustomerId).collect(Collectors.toList());
        lsAccount = AccountDao.list();
        lsAccountId = lsAccount.stream().map(Account::getAccountNumber).collect(Collectors.toList());
        transactions = TransactionDao.list();
        for (Customer cus : customers) {
            map.put(cus, lsAccount.stream().filter(item -> item.getCustomerId().equals(cus.getCustomerId())).collect(Collectors.toList()));
        }
    }


    public void showCustomers() throws IOException {
        if (map.isEmpty()) {
            System.out.println("Chưa có khách hàng");
            return;
        }
        for (Customer cus : customers) {
            cus.setAccounts(map.get(cus));
            cus.displayInformation();
        }
    }

    public void addCustomers(String fileName) throws IOException {
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("File nay ko ton tai.");
            return;
        }
        List<List<String>> lists = TextFileService.readFile(fileName);
        List<Customer> customersInput = lists.stream().map(item -> new Customer(item.get(0), item.get(1))).collect(Collectors.toList());
        List<String> customerIds = customers.stream().map(User::getCustomerId).collect(Collectors.toList());
        for (Customer cus : customersInput) {
            if (!CheckCustomerIdUtils.checkCCCD(cus.getCustomerId())) {
                System.out.println("Customer co id: " + cus.getCustomerId() + " sai thong tin");
                continue;
            } else if (customerIds.contains(cus.getCustomerId())) {
                System.out.println("Customer co id: " + cus.getCustomerId() + " da ton tai");
                continue;
            } else {
                System.out.println("Da them moi tai khoan co id: " + cus.getCustomerId());
                customers.add(cus);
            }
        }
        CustomerDao.save(customers);
    }

    public void addSavingAccount(String customerId) throws IOException {
        if (!lsCustomerId.contains(customerId)) {
            System.out.println("Khong tim thay khach hang " + customerId + ", tac vu khong thanh cong");
            return;
        }
        String accountNumber = inputAccountNumber();
        double balance = inputBalance();
        Account account = new Account(customerId, accountNumber, balance);
        AccountDao.update(account);
        List<Transaction> transactions = TransactionDao.list();
        Transaction transaction = new Transaction(String.valueOf(transactions.size()), accountNumber, balance, String.valueOf(LocalDateTime.now()), true, TransactionType.DEPOSIT);
        transactions.add(transaction);
        TransactionDao.save(transactions);
        System.out.println("Them tai khoan " + accountNumber + " thanh cong");
    }

    public double inputBalance() {
        while (true) {
            System.out.print("Nhap so du tai khoan >= 50000đ: ");
            String balace = scanner.nextLine();
            // check full so
            String[] strings = balace.trim().split("");
            for (String s : strings) {
                try {
                    Integer.parseInt(s);
                } catch (Exception e) {
                    continue;
                }
            }
            if (Double.parseDouble(balace) < 50000)
                continue;
            return Double.parseDouble(balace);
        }
    }

    public String inputAccountNumber() throws IOException {
        while (true) {
            System.out.print("Nhap tai khoan gom 6 chu: ");
            String accountNumber = scanner.nextLine();
            //check do dai
            if (accountNumber.trim().length() != 6)
                continue;
            //check full so
            String[] strings = accountNumber.trim().split("");
            for (String s : strings) {
                try {
                    Integer.parseInt(s);
                } catch (Exception e) {
                    continue;
                }
            }
            //check accountnumber ton tai chua
            if (lsAccountId.contains(accountNumber)) {
                System.out.println("account number này đã tồn tại");
                continue;
            }
            return accountNumber;
        }
    }

    public void withdraw(String customerId) throws IOException {
        if (!lsCustomerId.contains(customerId)) {
            System.out.println("Khong tim thay khach hang " + customerId + ", tac vu khong thanh cong");
            return;
        }
        Customer customer = getCustomerById(customerId);
        customer.displayInformation();
        customer.withdraw(scanner);
        System.out.println("Rut tien thanh cong, bien lai giao dich.");
    }

    public void tranfers(String customerId) throws IOException, CustomerIdNotValidException {
        CustomerIdNotValidException.checkCustomerId(customerId);
        Customer customer = getCustomerById(customerId);
        customer.displayInformation();
        customer.transfers(scanner);
    }

    public boolean isAccountExisted(Account newAccount) {
        List<String> lsAccountNumber = lsAccount.stream().map(Account::getAccountNumber).collect(Collectors.toList());
        return lsAccountNumber.contains(newAccount.getAccountNumber());
    }

    public boolean isCustomerExisted(Customer newCustomer) {
        List<String> customerNumbers = customers.stream().map(User::getCustomerId).collect(Collectors.toList());
        return customerNumbers.contains(newCustomer.getCustomerId());
    }

    public Customer getCustomerById(String customerId) {


        Map<String, List<Account>> listMap = lsAccount.stream().collect(Collectors.groupingBy(Account::getCustomerId));
        Customer customer = customers.stream().filter(item -> item.getCustomerId().equals(customerId)).findFirst().get();

        customer.setAccounts(listMap.get(customerId));
        return customer;
    }

    public double inputBalanceChuyen(Account account) throws IOException {
        while (true) {
            System.out.print("Nhap tien muon chuyen(so du sau khi chuyen >= 50000đ): ");
            String sotienString = scanner.nextLine();
            try {
                double sotien = Double.parseDouble(sotienString);
                if (account.getBalance() - sotien < 50000)
                    continue;
                return sotien;
            } catch (Exception e) {
                continue;
            }
        }
    }

    public Customer getByAccountNumber(String accountNumber) {
        Customer customer = map.keySet().stream().filter(item -> map.get(item).stream().anyMatch(i -> i.getAccountNumber().equals(accountNumber))).findFirst().get();
        return customer;
    }


}
