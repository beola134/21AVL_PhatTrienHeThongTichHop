//javac -d out src\bai4\*.java
//java -cp out bai4.ContactServer
//java -cp out bai4.ContactClient
package bai4;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ContactServer {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            ContactService service = new ContactServiceImpl();
            registry.rebind("ContactService", service);

            System.out.println("RMI Contact Server đang chạy tại cổng 1099...");
        } catch (Exception e) {
            System.err.println("Lỗi Server: " + e.getMessage());
            e.printStackTrace();
        }
    }
}