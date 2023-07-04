package vn.funix.fx21678.java.asm04.common;

import vn.funix.fx21678.java.asm04.model.TransactionType;

public interface IReport {
    void log(double amount, TransactionType type, String receiveAccount);
}
