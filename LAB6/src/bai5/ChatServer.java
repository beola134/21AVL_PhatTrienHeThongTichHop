//javac -d out src\bai5\*.java
//java -cp out bai5.StudentServer
//java -cp out bai5.StudentClient
package bai5;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ChatServer {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            ChatService service = new ChatServiceImpl();
            registry.rebind("ChatService", service);

            System.out.println("RMI Chat Server đang chạy tại cổng 1099 và sẵn sàng nhận tin nhắn...");
        } catch (Exception e) {
            System.err.println("Lỗi Server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}