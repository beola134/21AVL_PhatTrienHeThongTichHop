package bai3;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeServer {
    // KHAI BÁO CỔNG LẮNG NGHE CHO TCP VÀ UDP
    private static final int TCP_PORT = 9998;
    private static final int UDP_PORT = 9997;

    public static void main(String[] args) {
        // Tách UDP Server chạy trên một Thread (luồng) riêng song song
        new Thread(DateTimeServer::startUdpServer).start();
        // Chạy TCP Server trên Thread chính (Main Thread)
        startTcpServer();
    }

    // LẮNG NGHE VÀ XỬ LÝ KẾT NỐI TCP
    private static void startTcpServer() {
        System.out.println("TCP Server dang chay tren cong " + TCP_PORT + "...");
        try (ServerSocket serverSocket = new ServerSocket(TCP_PORT)) {
            while (true) {
                // Chờ Client TCP kết nối
                Socket clientSocket = serverSocket.accept();
                System.out.println("[TCP] Da ket noi voi client: " + clientSocket.getRemoteSocketAddress());
                // Tạo Thread mới xử lý riêng cho từng Client kết nối tới (đa luồng)
                new Thread(() -> handleTcpClient(clientSocket)).start();
            }
        } catch (IOException e) {
            System.err.println("Loi TCP Server: " + e.getMessage());
        }
    }

    // Luồng xử lý giao tiếp TCP với một Client
    private static void handleTcpClient(Socket socket) {
        try (
                // nhận và gửi dữ liệu từ socket
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {
            String request;
            // Đọc liên tục từng dòng lệnh từ Client gửi lên
            while ((request = reader.readLine()) != null) {
                request = request.trim().toUpperCase();
                if ("QUIT".equals(request)) {
                    writer.write("TAMBIET\n");
                    writer.flush();
                    break;
                }
                // Xử lý lấy ngày/giờ và gửi phản hồi
                String response = processRequest(request);
                writer.write(response + "\n");
                writer.flush();
            }
        } catch (IOException e) {
            System.err.println("[TCP] Mat ket noi client: " + e.getMessage());
        } finally {
            try {
                // Đảm bảo đóng socket sau khi ngắt kết nối
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    // LẮNG NGHE VÀ XỬ LÝ GÓI UDP
    private static void startUdpServer() {
        System.out.println("UDP Server dang chay tren cong " + UDP_PORT + "...");
        try (DatagramSocket socket = new DatagramSocket(UDP_PORT)) {
            byte[] buffer = new byte[1024];// Bộ đệm nhận dữ liệu
            while (true) {
                // nhận gói tin (DatagramPacket) gửi tới cổng 9997
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);// Tạm dừng cho đến khi có gói tin gửi tới
                // Chuyển mảng byte nhận được thành chuỗi văn bản
                String request = new String(packet.getData(), 0, packet.getLength(), StandardCharsets.UTF_8).trim()
                        .toUpperCase();
                System.out.println("[UDP] Nhan yeu cau '" + request + "' tu " + packet.getSocketAddress());
                // Xử lý lấy ngày/giờ
                String response = processRequest(request);
                byte[] responseData = response.getBytes(StandardCharsets.UTF_8);
                //Đóng gói dữ liệu phản hồi kèm theo IP và Cổng của Client gửi tới
                DatagramPacket responsePacket = new DatagramPacket(
                        responseData, responseData.length,
                        packet.getAddress(), packet.getPort());
                // Gửi gói tin trả về cho Client
                socket.send(responsePacket);
            }
        } catch (IOException e) {
            System.err.println("Loi UDP Server: " + e.getMessage());
        }
    }

    private static String processRequest(String cmd) {
        LocalDateTime now = LocalDateTime.now();
        switch (cmd) {
            case "DATE":
                return now.format(DateTimeFormatter.ofPattern("dd MM yyyy"));
            case "TIME":
                return now.format(DateTimeFormatter.ofPattern("HH mm ss"));
            case "DATETIME":
                return now.format(DateTimeFormatter.ofPattern("dd MM yyyy HH mm ss"));
            default:
                return "ERR INVALID_COMMAND";
        }
    }
}