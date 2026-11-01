package bai6;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class BankClient {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            BankService service = (BankService) registry.lookup("BankService");

            int choice;
            do {
                System.out.println("\n--- HỆ THỐNG NGÂN HÀNG RMI ---");
                System.out.println("1. Kiểm tra số dư");
                System.out.println("2. Gửi tiền vào tài khoản");
                System.out.println("3. Rút tiền từ tài khoản");
                System.out.println("0. Thoát");
                System.out.print("Chọn chức năng (0-3): ");

                choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        double balance = service.getBalance();
                        System.out.println("-> Số dư hiện tại của bạn là: " + balance + " VNĐ");
                        break;

                    case 2:
                        System.out.print("Nhập số tiền cần gửi: ");
                        double depositAmount = scanner.nextDouble();
                        if (depositAmount > 0) {
                            service.deposit(depositAmount);
                            System.out.println("-> Gửi tiền thành công!");
                        } else {
                            System.out.println("-> Gửi tiền thất bại: Số tiền gửi phải lớn hơn 0.");
                        }
                        break;

                    case 3:
                        System.out.print("Nhập số tiền cần rút: ");
                        double withdrawAmount = scanner.nextDouble();
                        boolean success = service.withdraw(withdrawAmount);
                        if (success) {
                            System.out.println("-> Rút tiền thành công!");
                        } else {
                            System.out.println("-> Rút tiền thất bại! (Kiểm tra lại số dư hoặc số tiền nhập)");
                        }
                        break;

                    case 0:
                        System.out.println("Cảm ơn quý khách đã sử dụng dịch vụ!");
                        break;

                    default:
                        System.out.println("Lựa chọn không hợp lệ! Vui lòng chọn từ 0 đến 3.");
                }
            } while (choice != 0);

        } catch (Exception e) {
            System.err.println("Lỗi Client: " + e.getMessage());
            e.printStackTrace();
        }
    }
}