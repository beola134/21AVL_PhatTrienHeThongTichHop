package bai5;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class ChatClient {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            ChatService service = (ChatService) registry.lookup("ChatService");

            System.out.println("--- ĐÃ KẾT NỐI ĐẾN CHAT SERVER ---");
            System.out.println("Nhập tin nhắn của bạn (gõ 'exit' hoặc 'quit' để thoát):");

            while (true) {
                System.out.print("Client > ");
                String message = scanner.nextLine();

                // Điều kiện thoát vòng lặp chat
                if (message.equalsIgnoreCase("exit") || message.equalsIgnoreCase("quit")) {
                    System.out.println("Đã ngắt kết nối chat.");
                    break;
                }

                // Gửi tin nhắn đến server và nhận lại phản hồi ngay trong lời gọi phương thức
                String response = service.sendMessage(message);

                System.out.println("Server > " + response);
            }

        } catch (Exception e) {
            System.err.println("Lỗi Client: " + e.getMessage());
            e.printStackTrace();
        }
    }
}