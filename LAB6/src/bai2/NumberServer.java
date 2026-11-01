package bai2;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class NumberServer {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            MathService service = new MathServiceImpl();
            registry.rebind("MathService", service);

            System.out.println("RMI Math Server đang chạy tại cổng 1099...");
        } catch (Exception e) {
            System.err.println("Lỗi Server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}