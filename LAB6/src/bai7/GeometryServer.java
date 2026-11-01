//javac -d out src\bai7\*.java
//java -cp out bai7.GeometryServer
//java -cp out bai7.GeometryClient
package bai7;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class GeometryServer {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            GeometryService service = new GeometryServiceImpl();
            registry.rebind("GeometryService", service);

            System.out.println("RMI Geometry Server đang chạy tại cổng 1099...");
        } catch (Exception e) {
            System.err.println("Lỗi Server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}