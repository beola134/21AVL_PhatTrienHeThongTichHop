package tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TcpCommandClient {
    public static void main(String[] args) {
        // CẤU HÌNH THAM SỐ KẾT NỐI
        String host = args.length > 0 ? args[0] : "localhost";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 5000;
        // THIẾT LẬP KẾT NỐI VÀ CÁC LUỒNG DỮ LIỆU UTF-8 (TỰ ĐỘNG ĐÓNG KHI KẾT THÚC)
        try (Socket socket = new Socket(host, port);
                // Đọc lệnh từ bàn phím người dùng
                BufferedReader console = new BufferedReader(
                        new InputStreamReader(System.in, StandardCharsets.UTF_8));
                // Đọc dữ liệu phản hồi do Server gửi về qua mạng
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                // Gửi dữ liệu từ Client tới Server
                PrintWriter out = new PrintWriter(
                        new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true)) {
            System.out.println("Connected. Enter PING, TIME, UPPER text, QUIT");
            String request;
            // VÒNG LẶP ĐỌC LỆNH TỪ BÀN PHÍM VÀ GỬI SANG SERVER
            while ((request = console.readLine()) != null) {
                out.println(request);
                String response = in.readLine();// Chờ và đọc phản hồi từ Server
                if (response == null) {
                    System.out.println("Server closed the connection");
                    break;
                }
                // Nếu vừa gõ lệnh QUIT thì ngắt vòng lặp, tự động đóng Socket
                System.out.println("Server: " + response);
                if (request.equalsIgnoreCase("QUIT"))
                    break;
            }
            // BẮT VÀ XỬ LÝ LỖI
        } catch (NumberFormatException e) {
            System.err.println("Port phai la so nguyen");
        } catch (IOException e) {
            System.err.println("Loi ket noi: " + e.getMessage());
        }
    }
}
// javac -d out src/tcp/TcpCommandServer.java src/tcp/TcpCommandClient.java
// java -cp out tcp.TcpCommandServer
// java -cp out tcp.TcpCommandClient localhost 5000