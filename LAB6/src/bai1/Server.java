// javac -d out src\bai1\*.java
// java -cp out bai1.Server
// java -cp out bai1.Client

package bai1;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            HelloService service = new HelloServiceImpl();
            registry.rebind("HelloService", service);

            System.out.println("RMI Server đang chạy tại cổng 1099...");
        } catch (Exception e) {
            System.err.println("Lỗi Server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}