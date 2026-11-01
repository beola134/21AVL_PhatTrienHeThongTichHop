//javac -d out src\bai6\*.java
//java -cp out bai6.BankServer
//java -cp out bai6.BankClient
package bai6;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class BankServer {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            // Khởi tạo tài khoản với số dư ban đầu là 1,000,000 VNĐ
            BankService service = new BankServiceImpl(1000000.0);
            registry.rebind("BankService", service);

            System.out.println("RMI Bank Server đang chạy tại cổng 1099 (Số dư ban đầu: 1,000,000 VNĐ)...");
        } catch (Exception e) {
            System.err.println("Lỗi Server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}