package vn.funix.fx21678.java.asm04.model;

import vn.funix.fx21678.java.asm04.common.ITransfer;
import vn.funix.fx21678.java.asm04.dao.AccountDao;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDateTime;

public class SavingAccount extends Account implements Serializable, ITransfer {

    public SavingAccount(Account acc) {
        this.setAccountNumber(acc.getAccountNumber());
        this.setBalance(acc.getBalance());
        this.setCustomerId(acc.getCustomerId());
    }

    public void withdraw(double amount) throws IOException {
        this.setBalance(this.getBalance() - amount);
        AccountDao.update(this);
        createTransaction(-amount, String.valueOf(LocalDateTime.now()), true, amount > 0 ? TransactionType.WITHDRAW : TransactionType.DEPOSIT);
    }

    @Override
    public void transfer(String recieveAccount, double amount) throws IOException {
        this.setBalance(this.getBalance() - amount);
        AccountDao.update(this);
        Account recieveAcc = Customer.getAccountByAccountNumber(AccountDao.list(), recieveAccount);
        recieveAcc.setBalance(recieveAcc.getBalance() + amount);
        AccountDao.update(recieveAcc);
        createTransaction(-amount, String.valueOf(LocalDateTime.now()), true, TransactionType.TRANSFERS);
        recieveAcc.createTransaction(amount, String.valueOf(LocalDateTime.now()), true, TransactionType.TRANSFERS);
    }
}
