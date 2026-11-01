package bai3;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class PrimeClient {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            PrimeService service = (PrimeService) registry.lookup("PrimeService");

            System.out.print("Nhập một số nguyên cần kiểm tra: ");
            int n = scanner.nextInt();

            boolean result = service.isPrime(n);

            if (result) {
                System.out.println("Kết quả: Số " + n + " LÀ số nguyên tố.");
            } else {
                System.out.println("Kết quả: Số " + n + " KHÔNG PHẢI là số nguyên tố.");
            }

        } catch (Exception e) {
            System.err.println("Lỗi Client: " + e.getMessage());
            e.printStackTrace();
        }
    }
}