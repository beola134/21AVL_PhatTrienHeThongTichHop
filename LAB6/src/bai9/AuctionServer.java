//javac -d out src\bai9\*.java
//java -cp out bai9.AuctionServer
//java -cp out bai9.AuctionClient
//java -cp out bai9.AuctionClient
package bai9;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class AuctionServer {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            AuctionService service = new AuctionServiceImpl();
            registry.rebind("AuctionService", service);

            System.out.println("RMI Auction Server đang chạy tại cổng 1099...");
        } catch (Exception e) {
            System.err.println("Lỗi Server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}