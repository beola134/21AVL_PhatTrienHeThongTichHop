package bai2;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class NumberClient {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            // Kết nối tới RMI Registry
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            MathService service = (MathService) registry.lookup("MathService");

            // Nhập dữ liệu từ bàn phím
            System.out.print("Nhập số nguyên a: ");
            int a = scanner.nextInt();

            System.out.print("Nhập số nguyên b: ");
            int b = scanner.nextInt();

            // Gọi phương thức từ xa
            int sum = service.add(a, b);

            // Hiển thị kết quả
            System.out.println("Kết quả (" + a + " + " + b + ") nhận từ Server là: " + sum);

        } catch (Exception e) {
            System.err.println("Lỗi Client: " + e.getMessage());
            e.printStackTrace();
        }
    }
}