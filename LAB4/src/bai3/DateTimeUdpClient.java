package bai3;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class DateTimeUdpClient {
    public static void main(String[] args) {
        String host = "127.0.0.1";
        int port = 9997;

        System.out.println("Khoi tao UDP Client giao tiep voi " + host + ":" + port);
        try (
                DatagramSocket socket = new DatagramSocket();
                Scanner scanner = new Scanner(System.in)) {
            InetAddress serverAddress = InetAddress.getByName(host);
            System.out.println("Nhap lenh (DATE, TIME, DATETIME) - Go QUIT de thoat:");

            while (true) {
                System.out.print("UDP Client nhap > ");
                String cmd = scanner.nextLine();

                if ("QUIT".equalsIgnoreCase(cmd.trim())) {
                    break;
                }

                byte[] sendData = cmd.getBytes(StandardCharsets.UTF_8);
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, port);
                socket.send(sendPacket);

                byte[] receiveBuffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveBuffer, receiveBuffer.length);

                socket.setSoTimeout(3000); // Thiet lap thoi gian cho phan hoi 3 giay
                try {
                    socket.receive(receivePacket);
                    String response = new String(receivePacket.getData(), 0, receivePacket.getLength(),
                            StandardCharsets.UTF_8);
                    System.out.println("Server phan hoi: " + response);
                } catch (SocketTimeoutException e) {
                    System.err.println("Loi: Qua thoi gian cho phan hoi tu UDP Server (Timeout hoac Server da tat).");
                }
            }
        } catch (IOException e) {
            System.err.println("Loi UDP Client: " + e.getMessage());
        }
    }
}