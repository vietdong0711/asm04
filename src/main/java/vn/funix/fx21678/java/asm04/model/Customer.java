package vn.funix.fx21678.java.asm04.model;

import vn.funix.fx21678.java.asm04.dao.AccountDao;

import java.io.IOException;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Customer extends User implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final String COMMA_DELIMITER = ",";
    List<Account> accounts;

    public Customer() {
    }

    public Customer(String[] arr) {
        this.setCustomerId(arr[0]);
        this.setName(arr[1]);
    }

    public Customer(String id, String name) {
        this.setCustomerId(id);
        this.setName(name);
    }

    public Customer(List<String> ls) {
        this.setCustomerId(ls.get(0));
        this.setName(ls.get(1));
    }

    public boolean isPremium() throws IOException {
        if (this.getAccounts() != null && !this.getAccounts().isEmpty()) {
            for (Account acc : accounts) {
                if (acc.isPremium())
                    return true;
            }
        }
        return false;
    }

    public List<Account> getAccounts() throws IOException {
        return AccountDao.list().stream().filter(item -> item.getCustomerId().equals(this.getCustomerId())).collect(Collectors.toList());
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public void displayInformation() throws IOException {
        Locale locale = new Locale("vi", "VI");
        String pattern = "###,###,###,###";
        DecimalFormat dcf = (DecimalFormat) NumberFormat.getNumberInstance(locale);
        dcf.applyPattern(pattern);
        double tong = 0;

        if (this.getAccounts() != null && !this.getAccounts().isEmpty()) {
            for (Account acc : this.getAccounts()) {
                tong += acc.getBalance();
            }
        }
        System.out.printf("%12s |%18s|%18s|%18s\n", this.getCustomerId(), this.getName(), this.isPremium() ? "Premium" : "Normal", dcf.format(tong) + "đ");
        int i = 1;
        if (this.getAccounts() != null && !this.getAccounts().isEmpty()) {
            for (Account acc : this.getAccounts()) {
                System.out.printf("%-3s%9s |%18s|%37s\n", i, acc.getAccountNumber(), "SAVINGS", dcf.format(acc.getBalance()) + "đ");
                i++;
            }
        }
    }

    public void displayTransactionInformation() throws IOException {
        this.displayInformation();
        for (Account a : getAccounts()) {
            a.displayTransactionsList();
        }
    }

    public void transfers(Scanner scanner) throws IOException {
        List<Account> accs = getAccounts();
        if (!accs.isEmpty()) {
            Account accountChuyen;
            String accountNhan;
            Account accNhan;
            double amount;

            do {
                System.out.println("Nhap so tai khoan chuyen tien: ");
                accountChuyen = getAccountByAccountNumber(accs, scanner.nextLine());
            } while (accountChuyen == null);
            do {
                System.out.println("Nhap so tai khoan nhan tien: ");
                accountNhan = scanner.nextLine();
                accNhan = getAccountByAccountNumber(AccountDao.list(), accountNhan);
            } while (accNhan == null || accountNhan.equals(accountChuyen.getAccountNumber()));
            do {
                System.out.println("Nhap so tien rut(sau khi rut >=50000đ): ");
                amount = Double.parseDouble(scanner.nextLine());
            } while (amount < 0 || !accountChuyen.isAccepted(amount));
            while (true) {
                System.out.print("Xac nhan chuyen " + amount + " từ tai khoan [" + accountChuyen.getAccountNumber() + "] den tai khoan [" + accountNhan + "] (Y/N): ");
                String choose = scanner.nextLine();
                switch (choose) {
                    case "Y":
                        SavingAccount accChuyen = new SavingAccount(accountChuyen);
                        accChuyen.transfer(accountNhan, amount);
                        System.out.println("Chuyen tien thanh cong. bien lai giao dich");
                        accChuyen.log(amount, TransactionType.TRANSFERS, accountNhan);
                        break;
                    case "N":
                        return;
                    default:
                        System.out.println("Nhap lai: ");
                        continue;
                }
                break;
            }

        }
    }

    public void withdraw(Scanner scanner) throws IOException {
        List<Account> accs = getAccounts();
        if (!accs.isEmpty()) {
            Account account;
            double amount;

            do {
                System.out.println("Nhap so tai khoan: ");
                account = getAccountByAccountNumber(accs, scanner.nextLine());
            } while (account == null);

            do {
                System.out.println("Nhap so tien rut: ");
                amount = Double.parseDouble(scanner.nextLine());
            } while (amount < 0 || !account.isAccepted(amount));
            SavingAccount savingAccount = new SavingAccount(account);
            savingAccount.withdraw(amount);
            System.out.println("Rut tien thanh cong. bien lai giao dich");
            account.log(amount, TransactionType.TRANSFERS, "");

        } else {
            System.out.println("Khach hang ko co tai khoan nao.");
        }
    }

    public static Account getAccountByAccountNumber(List<Account> accounts, String accNumber) {
        Optional<Account> optionalAccount = accounts.stream().filter(item -> item.getAccountNumber().equals(accNumber)).findFirst();
        return optionalAccount.isPresent() ? optionalAccount.get() : null;
    }

}
