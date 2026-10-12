import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (
                Socket socket = new Socket("localhost", 5000);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                Scanner sc = new Scanner(System.in, "UTF-8")) {
            // Nhập tên client
            System.out.print("Nhập tên của bạn: ");
            String name = sc.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("Tên không được để trống!");
                return;
            }

            out.println(name);

            // Luồng nhận tin nhắn từ máy chủ
            Thread receiver = new Thread(() -> {
                try {
                    String message;
                    while ((message = in.readLine()) != null) {
                        System.out.println("\n" + message);
                        System.out.print("> ");
                    }
                } catch (IOException e) {
                    if (!socket.isClosed()) {
                        System.out.println("\nLỗi nhận dữ liệu từ máy chủ: " + e.getMessage());
                    }
                }
            });

            receiver.setDaemon(true);
            receiver.start();

            // Gửi lựa chọn phòng và tin nhắn
            while (sc.hasNextLine()) {
                String message = sc.nextLine().trim();

                out.println(message);

                if (message.equalsIgnoreCase("/exit")) {
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("Không thể kết nối máy chủ: " + e.getMessage());
        }
    }
}