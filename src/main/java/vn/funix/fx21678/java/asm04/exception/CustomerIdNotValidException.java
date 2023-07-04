package vn.funix.fx21678.java.asm04.exception;

import vn.funix.fx21678.java.asm04.utils.CheckCustomerIdUtils;

import java.time.Year;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class CustomerIdNotValidException extends Exception {

    public CustomerIdNotValidException(String message) {
        super(message);
    }

    public static void checkCustomerId(String cccd) throws CustomerIdNotValidException {
        if (!CheckCustomerIdUtils.checkCCCD(cccd)) {
            throw new CustomerIdNotValidException("Khong tim thay khach hang " + cccd + ", tac vu khong thanh cong");
        }
    }
}
