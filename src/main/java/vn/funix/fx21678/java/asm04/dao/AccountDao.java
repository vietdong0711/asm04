package vn.funix.fx21678.java.asm04.dao;

import vn.funix.fx21678.java.asm04.model.Account;
import vn.funix.fx21678.java.asm04.service.BinaryFileService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AccountDao {

    private static final String FILE_PATH = "store/accounts.dat";

    public static void save(List<Account> accounts) throws IOException {
        BinaryFileService.writeFile(FILE_PATH, accounts);
    }

    public static List<Account> list() throws IOException {
        return BinaryFileService.readFile(FILE_PATH);
    }

    public static void update(Account account) throws IOException {

        ExecutorService  executorService = Executors.newFixedThreadPool(10);
        List<Account> accounts = list();
        boolean hasExist = accounts.stream().anyMatch(item -> item.getAccountNumber().equals(account.getAccountNumber()));
        executorService.shutdown();


        List<Account> accountsUpdate;
        if (!hasExist) {
            accountsUpdate = new ArrayList<>(accounts);
            accountsUpdate.add(account);
        } else {
            accountsUpdate = new ArrayList<>();
            for (Account acc : accounts) {
                accountsUpdate.add(acc.getAccountNumber().equals(account.getAccountNumber()) ? account : acc);
            }
        }
        save(accountsUpdate);
    }
}
