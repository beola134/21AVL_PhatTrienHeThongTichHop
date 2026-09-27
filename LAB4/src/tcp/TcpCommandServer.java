package tcp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Locale;

public class TcpCommandServer {
    private static final int PORT = 5000;// KHAI BÁO CỔNG LẮNG NGHE

    public static void main(String[] args) {
        // LẮNG NGHE KẾT NỐI VÀ TIẾP NHẬN CLIENT
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("TCP server listening on port " + PORT);
            while (true) {
                //// Tự động đóng Socket kết nối của Client sau khi xử lý xong một phiên
                try (Socket socket = server.accept()) {
                    serve(socket);// Chuyển kết nối cho phương thức xử lý
                } catch (IOException e) {
                    System.err.println("Loi phien client: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Khong mo duoc server: " + e.getMessage());
        }
    }

    // ĐỌC/GHI DỮ LIỆU ĐIỀU HƯỚNG PHIÊN LÀM VIỆC CỦA CLIENT
    static void serve(Socket socket) throws IOException {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(
                socket.getInputStream(), StandardCharsets.UTF_8));
                PrintWriter out = new PrintWriter(new OutputStreamWriter(
                        socket.getOutputStream(), StandardCharsets.UTF_8), true)) {
            String request;
            // Đọc từng dòng lệnh Client gửi lên
            while ((request = in.readLine()) != null) {
                String response = process(request);// Phân tích lệnh và lấy kết quả
                out.println(response);// Gửi phản hồi lại Client
                if (request.equalsIgnoreCase("QUIT"))
                    break;// Thoát phiên làm việc nếu nhận lệnh QUIT
            }
        }
    }

    // XỬ LÝ CÁC CÂU LỆNH ĐƯỢC GỬI TỪ CLIENT
    static String process(String request) {
        String trimmed = request.trim();
        if (trimmed.equalsIgnoreCase("PING"))
            return "OK PONG";
        if (trimmed.equalsIgnoreCase("TIME")) {
            return "OK " + LocalDateTime.now();
        }
        if (trimmed.equalsIgnoreCase("QUIT"))
            return "OK BYE";
        // Lệnh UPPER -> Kiểm tra 6 ký tự đầu tiên có phải "UPPER " hay không (khôngphân
        // biệt hoa/thường)
        if (trimmed.regionMatches(true, 0, "UPPER ", 0, 6)) {
            return "OK " + trimmed.substring(6).toUpperCase(Locale.ROOT);
        }
        return "ERR UNKNOWN_COMMAND";
    }
}
// javac -d out src/tcp/TcpCommandServer.java src/tcp/TcpCommandClient.java
// java -cp out tcp.TcpCommandServer
// java -cp out tcp.TcpCommandClient localhost 5000