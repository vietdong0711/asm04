package vn.funix.fx21678.java.asm04;

import vn.funix.fx21678.java.asm04.exception.CustomerIdNotValidException;
import vn.funix.fx21678.java.asm04.model.Customer;
import vn.funix.fx21678.java.asm04.model.DigitalBank;

import java.io.IOException;
import java.util.Scanner;

public class Asm04 {
    public static DigitalBank digitalBank;
    public static Scanner scanner;

    public Asm04() throws IOException {
        digitalBank = new DigitalBank();
        scanner = new Scanner(System.in);
    }

    public static void main(String[] args) throws IOException, CustomerIdNotValidException {


        while (true) {
            Asm04 asm04 = new Asm04();
            System.out.println("+----------+-------------------------+---------+");
            System.out.println("| NGAN HANG SO | FX21678@v4.0.0                |");
            System.out.println("+----------+-------------------------+---------+");
            System.out.printf("|%-46s|\n", "1. Xem danh sach khach hang");
            System.out.printf("|%-46s|\n", "2. Nhap danh sach khach hang");
            System.out.printf("|%-46s|\n", "3. Them tai khoan ATM");
            System.out.printf("|%-46s|\n", "4. Chuyen tien");
            System.out.printf("|%-46s|\n", "5. Rut tien");
            System.out.printf("|%-46s|\n", "6. Tra cuu lich su giao dich");
            System.out.printf("|%-46s|\n", "0. Thoát");
            System.out.println("+----------+-------------------------+---------+");
            System.out.println("Chuc nang: ");
            String key = scanner.nextLine();
            switch (key) {
                case "1":
                    System.out.println("Xem ds");
                    digitalBank.showCustomers();
                    break;
                case "2":
                    System.out.println("Nhap ds khach hang. Nhap dia chi tep: ");
                    String path = scanner.nextLine();
                    digitalBank.addCustomers(path);
                    break;
                case "3":
                    System.out.println("them tai khoan ATM");
                    System.out.println("Nhap ma so khach hang: ");
                    String customerId = scanner.nextLine();
                    digitalBank.addSavingAccount(customerId);
                    break;
                case "4":
                    System.out.println("chuyen tien");
                    System.out.println("Nhap ma so khach hang: ");
                    String cusId = scanner.nextLine();
                    digitalBank.tranfers(cusId);
                    break;
                case "5":
                    System.out.println("rut tien");
                    System.out.println("Nhap ma so khach hang: ");
                    String cussId = scanner.nextLine();
                    digitalBank.withdraw(cussId);
                    break;
                case "6":
                    System.out.println("tra cuu lich su");
                    System.out.println("Nhap ma so khach hang: ");
                    String cusssId = scanner.nextLine();
                    //check ton tai
                    Customer c = new Customer();
                    c.setCustomerId(cusssId);
                    if (!digitalBank.isCustomerExisted(c)) {
                        System.out.println("Khong tim thay khach hang " + cusssId + ", tac vu khong thanh cong");
                        break;
                    }
                    digitalBank.getCustomerById(cusssId).displayTransactionInformation();
                    break;

                default:
            }
        }
    }
}
