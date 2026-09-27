package bai2;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;

public class NumberServer {
    private static final int PORT = 9999;

    public static void main(String[] args) {
        System.out.println("Dang khoi chay TCP Server tren cong " + PORT + "...");
        // Mở ServerSocket lắng nghe cổng 9999
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {// Vòng lặp chờ nhận kết nối từ các Client
                Socket clientSocket = serverSocket.accept();
                System.out.println("Da ket noi voi client: " + clientSocket.getRemoteSocketAddress());
                // Chuyển socket client cho hàm xử lý
                handleClient(clientSocket);
            }
        } catch (IOException e) {
            System.err.println("Loi Server: " + e.getMessage());
        }
    }

    // TRAO ĐỔI DỮ LIỆU VỚI TỪNG CLIENT
    private static void handleClient(Socket socket) {
        // Mở các luồng đọc/ghi dữ liệu UTF-8 qua mạng với Client
        try (
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {
            String inputLine;
            // Đọc liên tục từng dòng Client gửi lên cho tới khi ngắt kết nối
            while ((inputLine = reader.readLine()) != null) {
                inputLine = inputLine.trim(); // Loai bo khoang trang thua neu co
                // Nếu Client gửi tín hiệu QUIT -> TAMBIET rồi thoát vòng lặp
                if ("QUIT".equalsIgnoreCase(inputLine)) {
                    writer.write("TAMBIET\n");
                    writer.flush();
                    break;
                }
                // Dịch ký tự số thành chữ tiếng Việt
                String response = translateDigit(inputLine);
                // Gửi kết quả dịch về Client
                writer.write(response + "\n");
                writer.flush();
            }
        } catch (IOException e) {
            System.err.println("Loi ket noi client: " + e.getMessage());
        } finally {
            // Đảm bảo luôn đóng Socket giải phóng tài nguyên sau khi Client thoát
            try {
                socket.close();
                System.out.println("Ngat ket noi client.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String translateDigit(String input) {
        // Kiem tra du lieu co phai dung mot chu so tu 0 den 9 khong
        if (input.length() != 1) {
            return "ERR INVALID_DIGIT";
        }

        char c = input.charAt(0);
        switch (c) {
            case '0':
                return "Khong";
            case '1':
                return "Mot";
            case '2':
                return "Hai";
            case '3':
                return "Ba";
            case '4':
                return "Bon";
            case '5':
                return "Nam";
            case '6':
                return "Sau";
            case '7':
                return "Bay";
            case '8':
                return "Tam";
            case '9':
                return "Chin";
            default:
                return "ERR INVALID_DIGIT";// Không phải ký tự số 0-9
        }
    }
}

// javac -d out src\bai2\NumberServer.java src\bai2\NumberClient.java
//java -cp out bai2.NumberServer
//java -cp out bai2.NumberClient