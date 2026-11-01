//javac -d out src\bai3\*.java
//java -cp out bai3.PrimeServer
//java -cp out bai3.PrimeClient
package bai3;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class PrimeServer {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            PrimeService service = new PrimeServiceImpl();
            registry.rebind("PrimeService", service);

            System.out.println("RMI Prime Server đang chạy tại cổng 1099...");
        } catch (Exception e) {
            System.err.println("Lỗi Server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}