//javac -d out src\bai8\*.java
//java -cp out bai8.FlightServer
//java -cp out bai8.FlightClient
package bai8;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class FlightServer {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            FlightService service = new FlightServiceImpl();
            registry.rebind("FlightService", service);

            System.out.println("RMI Flight Server đang chạy tại cổng 1099...");
        } catch (Exception e) {
            System.err.println("Lỗi Server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}