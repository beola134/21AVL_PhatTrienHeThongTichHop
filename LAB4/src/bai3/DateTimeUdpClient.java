package bai3;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class DateTimeUdpClient {
    public static void main(String[] args) {
        // CẤU HÌNH CỔNG VÀ ĐỊA CHỈ IP CỦA UDP
        String host = "127.0.0.1";
        int port = 9997; // Cổng UDP trùng khớp với DateTimeServe

        System.out.println("Khoi tao UDP Client giao tiep voi " + host + ":" + port);
        try (
                // Mở cổng UDP ngẫu nhiên
                DatagramSocket socket = new DatagramSocket();
                Scanner scanner = new Scanner(System.in)) {
            // Chuyển chuỗi IP thành đối tượng InetAddress
            InetAddress serverAddress = InetAddress.getByName(host);
            System.out.println("Nhap lenh (DATE, TIME, DATETIME) - Go QUIT de thoat:");
            // TRAO ĐỔI GÓI TIN UDP TƯƠNG TÁC
            while (true) {
                System.out.print("UDP Client nhap > ");
                String cmd = scanner.nextLine();

                if ("QUIT".equalsIgnoreCase(cmd.trim())) {
                    break;
                }
                // Đóng gói và gửi DatagramPacket tới Server
                byte[] sendData = cmd.getBytes(StandardCharsets.UTF_8);
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, port);
                socket.send(sendPacket); // gửi đi
                // Chuẩn bị bộ đệm nhận gói tin trả về
                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);

                socket.setSoTimeout(3000); // Thiet lap thoi gian cho phan hoi 3 giay
                try {
                    socket.receive(receivePacket); // tạm dừng
                    String response = new String(receivePacket.getData(), 0, receivePacket.getLength(),
                            StandardCharsets.UTF_8);
                    System.out.println("Server phan hoi: " + response);
                } catch (SocketTimeoutException e) {
                    // Nếu quá 3 giây không nhận được phản hồi
                    System.err.println("Loi: Qua thoi gian cho phan hoi tu UDP Server (Timeout hoac Server da tat).");
                }
            }
        } catch (IOException e) {
            System.err.println("Loi UDP Client: " + e.getMessage());
        }
    }
}

// javac -d out src\bai3\DateTimeServer.java src\bai3\DateTimeTcpClient.java src\bai3\DateTimeUdpClient.java
// java -cp out bai3.DateTimeServer
// java -cp out bai3.DateTimeUdpClient
// java -cp out bai3.DateTimeTcpClient
