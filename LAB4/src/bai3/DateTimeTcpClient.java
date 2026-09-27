package bai3;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class DateTimeTcpClient {
    public static void main(String[] args) {
        // CẤU HÌNH ĐỊA CHỈ IP VÀ CỔNG TCP SERVER
        String host = "127.0.0.1";
        int port = 9998;

        System.out.println("Dang ket noi TCP toi Server " + host + ":" + port + "...");
        try (
                // Mở Socket kết nối TCP đến Server
                Socket socket = new Socket(host, port);
                // đọc dữ liệu từ Server
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
                // gửi dữ liệu từ Client lên Server
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
                Scanner scanner = new Scanner(System.in)) {
            System.out.println("Ket noi TCP thanh cong! Nhap lenh (DATE, TIME, DATETIME, QUIT):");
            // VÒNG LẶP TRAO ĐỔI LỆNH TƯƠNG TÁC
            while (true) {
                System.out.print("TCP Client nhap > ");
                String cmd = scanner.nextLine();
                // Gửi câu lệnh lên Server
                writer.write(cmd + "\n");
                writer.flush(); // Đẩy dữ liệu đi ngay lập tức
                // Nhận phản hồi từ Server
                String res = reader.readLine();
                if (res == null) {
                    System.out.println("Server da ngat ket noi dot ngot!");
                    break;
                }
                System.out.println("Server phan hoi: " + res);

                if ("TAMBIET".equalsIgnoreCase(res) || "QUIT".equalsIgnoreCase(cmd)) {
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Loi TCP Client: " + e.getMessage());
        }
    }
}